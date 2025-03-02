**News Aggregator App**

Had Set up a basic Spring Boot project with necessary dependencies (Spring Web, Spring Security, etc.).

Implemented user registration and login using Spring Security with JWT for token-based authentication.

Uses an in-memory H2 database to store user information and their news preferences.

Implemented a RESTful API with the following endpoints:

POST /api/register: Register a new user.

POST /api/login: Log in a user.

GET /api/preferences: Retrieve the news preferences for the logged-in user.

PUT /api/preferences: Update the news preferences for the logged-in user.

GET /api/news: Fetch news articles based on the logged-in user's preferences.

Used external news APIs (NewsAPI ) to fetch news articles from multiple sources. Utilized Spring's WebClient for asynchronous HTTP requests.

Implemented proper exception handling for invalid requests, authentication errors, and authorization errors.

Added input validation for user registration and news preference updates using Spring's validation annotations.

Test the API using Postman to ensure it works as expected.

