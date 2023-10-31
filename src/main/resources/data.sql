INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('User2', 'user2@yandex.ru', '{noop}password2'),
       ('User3', 'user3@yandex.ru', '{noop}password3'),
       ('User4', 'user4@yandex.ru', '{noop}password4'),
       ('User5', 'user5@yandex.ru', '{noop}password5');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7);

INSERT INTO RESTAURANT (name, address)
VALUES ('Sabine', 'St. Paul''s, 10 Godliman St, London, England, United Kingdom, 7'),
       ('Aqua Shard', 'Level 31, The Shard, 31 St Thomas St, London SE1 9RY, United Kingdom'),
       ('Searcys at The Gherkin', 'The Gherkin, 30 St Mary Axe, London EC3A 8BF, United Kingdom'),
       ('Tavolino', '2 More London Pl, London SE1 2RR, United Kingdom'),
       ('Gillray’s Steakhouse', 'County Hall, Westminster Bridge Rd, London SE1 7PB, United Kingdom');

INSERT INTO DISH (name, price, description, calories, dish_date, restaurant_id)
VALUES ('Gouda & Bacon Goughnuts', 15, 'Siracha & maple mayo, bacon dust', 1688, now(), 1),
       ('Tostadas', 15, 'BBQ jackfruit, guacamole, pico de gallo, crisp onion', 1680, now(), 1),
       ('Chili and coriander beef short rib', 22, 'Thai salad, coriander chermoula', 544, now(), 1),

       ('Potato Gnocchi', 11, 'Ceps purée, mushroom ragout, coffee dust', 1204, now(), 2),
       ('Black Angus Beef Fillet', 28, 'Hen of the woods, roasted Grelot onion, parsley, red wine jus', 850, now(), 2),
       ('Pan-Seared Wild Halibut', 16, 'Courgette, young basil, marinated squash blossom', 655, now(), 2),

       ('Gressingham duck', 21, 'Smoked duck breast, charred orange, radicchio leaves, pomegranate molasses', 700, now(), 3),
       ('Vegetable broth', 9, 'Roasted winter vegetables, pearl barley, wild nettle pesto, toasted sourdough', 652, now(), 3),
       ('Pan seared Orkney scallops', 18, 'Cumin and turmeric red lentils, heritage radishes and dandelion leaves', 590, now(), 3),

       ('Meatball Parmesan Sliders', 16, 'House Made Meatballs, Provolone, San Marzano Tomato Gravy', 1001, now(), 4),
       ('Tuscan Chicken', 18, '8oz Wagyu Patty, Romaine, Tomato, House Made Pickles, Brioche Bun', 856, now(), 4),
       ('Old School Sausage & Peppers', 17, 'Fennel Sausage, Caramelized Onions, Roasted Red Pepper, San Marzano Tomato Gravy, Toasted Baguette', 963, now(), 4),

       ('For The Sea Food And Surf & Turf Lover', 45, 'Grilled Lobster Whole', 378, now() -5, 5),
       ('The Westminster’ Beef Flank Sandwich', 26, 'Iceberg, Horseradish Crème Fraiche, Piccalilli, Sour Dough', 737, now(), 5),
       ('Gillray’s Wagyu Steak Burger', 28, 'Brioche Bun, Smoked Cheddar, Bacon Jam, Pickle, Lettuce, Tomato', 847, now(), 5);

INSERT INTO VOTE (vote_date, vote_time, user_id, restaurant_id)
VALUES (now(), '8:00', 1, 3),
       (now(), '9:00', 7, 3),
       (now(), '10:00', 6, 4),
       (now(), '11:00', 4, 1),
       (now(), '12:00', 5, 2);
