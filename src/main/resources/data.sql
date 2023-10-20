INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (name)
VALUES ('Sabine'),
       ('Aqua Shard'),
       ('Searcys at The Gherkin'),
       ('Tavolino'),
       ('Gillray’s Steakhouse');

INSERT INTO DISHES (name, price, description, calories, date, restaurant_id)
VALUES ('Gouda & Bacon Goughnuts', 15, 'Siracha & maple mayo, bacon dust', 1688, '2023-01-01', 1),
       ('Tostadas', 15, 'BBQ jackfruit, guacamole, pico de gallo, crisp onion', 1680, '2023-01-01', 1),
       ('Chili and coriander beef short rib', 22, 'Thai salad, coriander chermoula', 544, '2023-01-01', 1),

       ('Potato Gnocchi', 11, 'Ceps purée, mushroom ragout, coffee dust', 1204, '2023-01-01', 2),
       ('Black Angus Beef Fillet', 28, 'Hen of the woods, roasted Grelot onion, parsley, red wine jus', 850, '2023-02-01', 2),
       ('Pan-Seared Wild Halibut', 16, 'Courgette, young basil, marinated squash blossom', 655, '2023-02-01', 2),

       ('Gressingham duck', 21, 'Smoked duck breast, charred orange, radicchio leaves, pomegranate molasses', 700, '2023-03-01', 3),
       ('Vegetable broth', 9, 'Roasted winter vegetables, pearl barley, wild nettle pesto, toasted sourdough', 652, '2023-03-01', 3),
       ('Pan seared Orkney scallops', 18, 'Cumin and turmeric red lentils, heritage radishes and dandelion leaves', 590, '2023-03-01', 3),

       ('Meatball Parmesan Sliders', 16, 'House Made Meatballs, Provolone, San Marzano Tomato Gravy', 1001, '2023-04-01', 4),
       ('Tuscan Chicken', 18, '8oz Wagyu Patty, Romaine, Tomato, House Made Pickles, Brioche Bun', 856, '2023-04-01', 4),
       ('Old School Sausage & Peppers', 17, 'Fennel Sausage, Caramelized Onions, Roasted Red Pepper, San Marzano Tomato Gravy, Toasted Baguette', 963, '2023-04-01', 4),

       ('For The Sea Food And Surf & Turf Lover', 45, 'Grilled Lobster Whole', 378, '2023-05-01', 5),
       ('‘The Westminster’ Beef Flank Sandwich', 26, 'Iceberg, Horseradish Crème Fraiche, Piccalilli, Sour Dough', 737, '2023-05-01', 5),
       ('Gillray’s Wagyu Steak Burger', 28, 'Brioche Bun, Smoked Cheddar, Bacon Jam, Pickle, Lettuce, Tomato', 847, '2023-05-01', 5);
