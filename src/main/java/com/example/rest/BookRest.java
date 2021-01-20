
package com.example.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("library")
public class BookRest {
    /**
     * Class for holding the list of customers and handling the requests
     */

    private static final ArrayList<Book> books = new ArrayList<>();

    /**
     * Meant for returning the list of customers
     * @return A concatenation of the toString method for all customers
     */
    @PUT
    @Path("insert")
    @Produces(MediaType.TEXT_PLAIN)
    public String putBook(@QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("isbn") String isbn) {
        if (title == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("title parameter is mandatory")
                            .build()
            );
        }

        if (author == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("author parameter is mandatory")
                            .build()
            );
        }

        if (isbn == null) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("isbn parameter is mandatory")
                            .build()
            );
        }

        Book result = books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (result != null) {
            result.setAuthor(author);
            result.setIsbn(isbn);

            return result.toString();
        }

        Book newBook = new Book(title, author, isbn);
        books.add(newBook);

        return newBook.toString();
    }

    /**
     * @param title of the book
     * @return toString method of book
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{title}")
    public String getBookByTitle(@PathParam("title") String title) {
        Book result = books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
        if (result != null) {
            return result.toString();
        } else {
            return "";
        }
    }
}