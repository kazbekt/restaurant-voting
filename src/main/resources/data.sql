INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (NAME, ID)
VALUES ('Wishbar', 10),
       ('Огонёк', 11),
       ('Swish Swish', 12),
       ('Ресторан без меню', 13);

INSERT INTO MEAL (NAME, PRICE, ID)
VALUES ('Рис отварной', 120.00, 20),
       ('Яичница', 150.00, 21),
       ('Шашлык по-пакистански', 1300.00, 22),
       ('Плов узбекский', 450.00, 23),
       ('Салат Греческий', 320.00, 24),
       ('Куриный суп', 280.00, 25),
       ('Стейк из лосося', 890.00, 26),
       ('Тирамису', 350.00, 27),
       ('Чай зеленый', 150.00, 28),
       ('Старое блюдо', 190.00, 29);

INSERT INTO MENU (DATE, RESTAURANT_ID, ID)
VALUES (NOW(), 10, 30),
       (NOW(), 11, 31),
       (NOW(), 12, 32),
       ('2025-10-01', 11, 33),
       ('2030-01-01', 10, 34);

INSERT INTO MENU_MEAL (MEAL_ID, MENU_ID)
VALUES (20, 30),
       (21, 30),
       (22, 30),
       (28, 30),
       (23, 31),
       (24, 31),
       (25, 31),
       (28, 31),
       (26, 32),
       (27, 32),
       (28, 32),
       (28, 33),
       (20, 34);

INSERT INTO VOTE (ID, RESTAURANT_ID, USER_ID, VOTE_DATE, VOTE_TIME)
VALUES (40, 10, 1, NOW(), '10:30:00');

CREATE INDEX IF NOT EXISTS idx_user_email ON users (email);
CREATE INDEX IF NOT EXISTS idx_menu_meal_menu_id ON menu_meal (MENU_ID);
CREATE INDEX IF NOT EXISTS idx_menu_restaurant_date ON menu (restaurant_id, date);
CREATE INDEX IF NOT EXISTS idx_vote_restaurant_date ON vote (restaurant_id, vote_date);
CREATE INDEX IF NOT EXISTS idx_vote_user_date ON vote (user_id, vote_date);
