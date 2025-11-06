[![Codacy Badge](https://app.codacy.com/project/badge/Grade/5e1d2c307d2c4276ba36b58260d0bded)](https://app.codacy.com/gh/kazbekt/restaurant-voting/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

<p align="center">
  <img src="src/main/resources/static/RestVoting.png" alt="Logo Placeholder" />
</p>


A REST API application for restaurant dinner voting. Users select the best menu for today and vote for the best restaurant for today's dinner. Each restaurant can post only one menu per day, and users can vote only once, but have the right to change their vote until 11:00 AM. Administrators manage restaurants, menus, and meals.

## <span style="color: #43a047;">Technology Stack</span>

### <span style="color: #e53935;">Backend</span>
- Java 21
- Spring Boot 3.5.6
- Spring Data JPA
- Spring Security (HTTP Basic)
- Spring Validation
- Spring Cache (Caffeine)

### <span style="color: #e53935;">Database</span>
- H2 (in-memory)

### <span style="color: #e53935;">API Documentation</span>
- SpringDoc OpenAPI (Swagger) 2.8.6
- Swagger UI

### <span style="color: #e53935;">Libraries</span>
- Lombok 1.18.30
- Jackson (with Hibernate6 and Money modules)
- Zalando Jackson Money 1.3.0
- Jsoup 1.21.2 (NoHTML validation)
- JUnit 5 Platform

### <span style="color: #e53935;">Build Tool</span>
- Maven

## <span style="color: #43a047;">REST API Documentation</span>

Swagger UI is available at: **http://localhost:8080/**

OpenAPI specification: **http://localhost:8080/v3/api-docs**

### <span style="color: #e53935;">Test Credentials</span>
- user@yandex.ru / password
- admin@gmail.com / admin
- guest@gmail.com / guest

### <span style="color: #e53935;">API Endpoints</span>

#### <span style="color: #fb8c00;">Profile Controller</span>
- `GET /api/profile` - Get current user profile
- `POST /api/profile` - Register new user
- `PUT /api/profile` - Update user profile
- `DELETE /api/profile` - Delete user profile

#### <span style="color: #fb8c00;">Restaurants</span>
- `GET /api/restaurants` - Get all restaurants
- `GET /api/restaurants/{id}` - Get restaurant by id
- `GET /api/restaurants/with-menus/today` - Get restaurants with today menu

#### <span style="color: #fb8c00;">Menus</span>
- `GET /api/menus` - Get all menus
- `GET /api/menus/{id}` - Get menu by id
- `GET /api/menus/by-restaurant` - Get menu by restaurant and date

#### <span style="color: #fb8c00;">Voting</span>
- `GET /api/profile/votes/today` - Get today vote
- `POST /api/profile/votes` - Create vote
- `PUT /api/profile/votes` - Update vote
- `DELETE /api/profile/votes` - Delete today vote

#### <span style="color: #fb8c00;">Admin - Users</span>
- `GET /api/admin/users` - Get all users
- `GET /api/admin/users/{id}` - Get user by id
- `GET /api/admin/users/by-email` - Get user by email
- `POST /api/admin/users` - Create user
- `PUT /api/admin/users/{id}` - Update user
- `DELETE /api/admin/users/{id}` - Delete user
- `PATCH /api/admin/users/{id}` - Enable/disable user

#### <span style="color: #fb8c00;">Admin - Restaurants</span>
- `GET /api/admin/restaurants` - Get all restaurants
- `GET /api/admin/restaurants/{id}` - Get restaurant by id
- `POST /api/admin/restaurants` - Create restaurant
- `PUT /api/admin/restaurants/{id}` - Update restaurant
- `DELETE /api/admin/restaurants/{id}` - Delete restaurant
- `GET /api/admin/restaurants/with-menus/today` - Get restaurants with today menu

#### <span style="color: #fb8c00;">Admin - Meals</span>
- `GET /api/admin/meals` - Get all meals
- `GET /api/admin/meals/{id}` - Get meal by id
- `POST /api/admin/meals` - Create meal
- `PUT /api/admin/meals/{id}` - Update meal
- `DELETE /api/admin/meals/{id}` - Delete meal

#### <span style="color: #fb8c00;">Admin - Menus</span>
- `GET /api/admin/menus` - Get all menus
- `POST /api/admin/menus` - Create menu
- `PUT /api/admin/menus/{id}` - Update menu
- `DELETE /api/admin/menus/{id}` - Delete menu

#### <span style="color: #fb8c00;">Admin - Votes</span>
- `GET /api/admin/votes/results/today` - Get today voting results
- `GET /api/admin/votes/results/by-date` - Get voting results by date
- `GET /api/admin/votes/user/{id}` - Get user voting history
