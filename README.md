# Installation
A Maven file is provided for installation. This project requires Java 8 in order to work.

# Configuration

Create the properties file.

- Under `src/main/resources`, copy `config.properties.dist` to `config.properties`.
- Supply a `username` and `password` to the config. This will be passed using Basic Auth with every request to the web service.
- Supply a Sendgrid API key.

# External frameworks

- Libraries
    - JAX-RS
    - Jersey
    - Hibernate
    - Grizzly
- Services
    - MySQL
    - The QR Code API
    - Google's SMTP Server
    - Sendgrid
        - sendgrid-java


[vscode]: https://code.visualstudio.com/