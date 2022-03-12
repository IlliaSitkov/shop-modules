
% provider(edrpou, name, address(country, region, city, street, house), contacts(phone_number, email)).
provider(35261345, "Colors", address(ukraine, kyivska, kyiv, green, 90), contacts("(0-800) 218-170", "colors@gmail.com")).
provider(45365542, "Flower", address(ukraine, lvivska, lviv, levova, 15), contacts("044-206-05-75", "flower@gmail.com")).
provider(83742733, "Tela Artis", address(ukraine, volynska, lutsk, floral, 31), contacts("067-626-09-60", "tela_artis@gmail.com")).
provider(65456345, "Green forest", address(usa, california, los_angeles, white, 12), contacts("567-234-23-12", "green_forest@gmail.com")).
provider(98675643, "Diamond", address(czech, praha, praha, legerova, 62), contacts("786-23-12", "diamond@gmail.com")).

% category(cat_number, name, description).
category(1, embroideries, "Beautiful embroideries").
category(2, drawing, "Everything for drawing").
category(3, knitting, "Knitting needles and threads").
category(4, modeling, "Everything for clay crafting").
category(5, scrapbooking, "Paper, tapes, glitter, glue and lots of other things").
category(6, papercrafting, "Everything for paper crafting").
category(7, beading, "Everything for beading").
category(8, burning, "Everything for burning").


% product(articul, name, description, quantity, price, edrpou, cat_number).
product(2433, "Embroidery with flowers", "Beads included", 5, 40, 83742733, 1).
product(2321, "Embroidery with cats", "Beads included", 3, 50, 65456345, 1).
product(5643, "White paper", "500 items in a pack", 20, 70, 35261345, 2).
product(4532, "Acrilic", "12 items in a pack", 12, 100, 35261345, 2).
product(2314, "Knitting needles", "A pair", 30, 50, 45365542, 3).
product(5648, "Knitting threads", "100 metres", 10, 40, 45365542, 3).
product(9864, "Clay", "Different colors", 40, 30, 45365542, 4).
product(9876, "Foamiran", "Different colors. For making flowers", 25, 10, 65456345, 4).
product(3421, "Tape", "Red, 1 metre", 10, 10, 83742733, 5).
product(8764, "Glitter", "Small pack", 15, 20, 98675643, 5).
product(1234, "Coloured Paper", "12 colours", 12, 22, 83742733, 6).
product(6165, "Coloured Beads", "6 colours", 3, 9, 45365542, 7).
product(6234, "Wooden decks", "10 pieces", 6, 97, 98675643, 8).


% предикат покупець(іпн,піб,адреса)
customer('000000000',full_name('Petrenko','Roman','Stepanovych'),address('Ukraine','Kharkiv','Izum','Zelena st.',5)).
customer('000000001',full_name('Vasylenko','Andriy','Vasyliovych'),address('Ukraine','Chernihiv','Pryluky','Vesela st.',12)).
customer('000000002',full_name('Sedash','Nina','Yuriivna'),address('Ukraine','Donetsk','Bakhmut','Pivdenna st.',23)).
customer('000000003',full_name('Trubeko','Tetyana','Vasylivna'),address('Ukraine','Vinnytsia','Tulchyn','Chervona st.',54)).
customer('000000004',full_name('Chaplyk','Dmytro','Vasyliovych'),address('Ukraine','Vinnytsia','Zhmerynka','Horbatova st.',45)).
customer('000000005',full_name('Volkov','Valentyn','Romanovych'),address('Ukraine','Odesa','Izmail','Vyshneva st.',9)).
customer('000000006',full_name('Vaha','Ludmila','Oleksiivna'),address('Ukraine','Zaporizhzhia','Zaporizhzhia','Chorna st.',65)).
customer('000000007',full_name('Zayarniy','Oleksandr','Hryhorovych'),address('Ukraine','Kyiv','Brovary','Holodna st.',43)).
customer('000000008',full_name('Andrushchenko','Stepan','Maksymovych'),address('Ukraine','Donetsk','Kramatorsk','Sumna st.',85)).
customer('000000009',full_name('Salnikov','Myhaylo','Serhiyovych'),address('Ukraine','Lviv','Lviv','Nova st.',30)).
customer('000000010',full_name('Titov','Roman','Dmytrovych'),address('Ukraine','Lviv','Holodna Voda','Tepla st.',71)).
customer('000000011',full_name('Lagutkova','Elizavetta','Serhiivna'),address('Ukraine','Dnipro','Kryviy Rih','Dovha st.',3)).
customer('000000012',full_name('Grinev','Pavlo','Oleksandrovych'),address('Ukraine','Kyiv','Kyiv','Soniachna st.',84)).
customer('000000013',full_name('Zozulia','Ivan','Ivanovych'),address('Ukraine','Mykolaiv','Mykolaiv','Poetychna st.',49)).
customer('000000014',full_name('Hryhorenko','David','Romanovych'),address('Ukraine','Donetsk','Slovyansk','Chysta st.',76)).

% предикат продавець(іпн,піб)
salesman('100000000',full_name('Petrov','Oleg','Ivanovych')).
salesman('200000000',full_name('Chernenko','Evgen','Stepanovych')).
salesman('300000000',full_name('Voloshyn','Petro','Nazarovych')).
salesman('400000000',full_name('Stepanov','Volodymyr','Mykolayovych')).
salesman('500000000',full_name('Fedorova','Kateryna','Yuriivna')).
salesman('600000000',full_name('Shevchenko','Valiria','Borysivna')).
salesman('700000000',full_name('Romanenko','Ivan','Antonovych')).

% предикат замовлення(номер_замовлення,іпн_продавця,іпн_покупця,дата_замовлення,статус_замовлення)
% First salesman for some time has one order with one order_row
%order(1,'100000000','000000001',date(30,1,2022),done).
%order(2,'100000000','000000001',date(31,1,2022),done).
order(3,'100000000','000000002',date(1,2,2022),in_progress).
%order(4,'100000000','000000002',date(2,2,2022),in_progress).
%order(5,'100000000','000000003',date(5,2,2022),new).

order(6,'200000000','000000004',date(31,1,2022),new).
order(7,'200000000','000000005',date(1,2,2022),done).
order(8,'200000000','000000006',date(3,2,2022),new).
order(9,'200000000','000000007',date(4,2,2022),in_progress).
order(10,'200000000','000000004',date(4,2,2022),in_progress).
order(11,'200000000','000000007',date(3,2,2022),done).

order(12,'300000000','000000014',date(31,1,2022),done).
order(13,'300000000','000000000',date(10,2,2022),new).
order(14,'300000000','000000001',date(8,2,2022),new).
order(15,'300000000','000000004',date(8,2,2022),in_progress).
order(16,'300000000','000000004',date(9,2,2022),in_progress).
order(17,'300000000','000000009',date(2,2,2022),new).

order(18,'400000000','000000011',date(29,1,2022),done).
order(19,'400000000','000000012',date(11,2,2022),new).
order(20,'400000000','000000011',date(9,2,2022),new).
order(21,'400000000','000000000',date(7,2,2022),in_progress).
order(22,'400000000','000000008',date(3,2,2022),in_progress).
order(23,'400000000','000000007',date(1,2,2022),new).
order(22,'400000000','000000014',date(4,2,2022),in_progress).
order(23,'400000000','000000002',date(7,2,2022),done).

order(24,'500000000','000000010',date(30,1,2022),done).
order(25,'500000000','000000011',date(8,2,2022),done).
order(26,'500000000','000000005',date(13,2,2022),done).
order(27,'500000000','000000000',date(7,2,2022),in_progress).
order(28,'500000000','000000007',date(5,2,2022),new).

order(29,'600000000','000000003',date(8,2,2022),done).
order(30,'600000000','000000004',date(4,2,2022),done).
order(31,'600000000','000000006',date(2,2,2022),in_progress).

order(32,'700000000','000000004',date(31,1,2022),done).
order(33,'700000000','000000011',date(31,1,2022),new).
order(34,'700000000','000000002',date(5,2,2022),new).


% order_row(order_id, articul, quantity).
order_row(1, 2321, 1).

order_row(2, 5648, 4).
order_row(2, 2314, 1).
order_row(2, 1234, 3).

% First salesman for some time has one order with one order_row
%order_row(3, 2433, 1).
%order_row(3, 2321, 1).
order_row(3, 5643, 2).

order_row(4, 8764, 3).
order_row(4, 9864, 3).
order_row(4, 3421, 6).
order_row(4, 4532, 2).

order_row(5, 5648, 2).
order_row(5, 2314, 1).
order_row(5, 2433, 1).

order_row(6, 5648, 3). % 3526
order_row(6, 8764, 1). %

order_row(7, 3421, 4).
order_row(7, 5643, 1).
order_row(7, 3421, 3).
order_row(7, 4532, 1).

order_row(7, 6234, 1).
order_row(7, 6165, 1).

% 8, 31 - orders for 000000006
%order_row(8, 9864, 5).%
%order_row(8, 9876, 2).%
order_row(8, 8764, 2).

order_row(9, 9864, 8).
order_row(9, 2321, 8).
order_row(9, 2433, 8).

order_row(10, 5643, 2).
order_row(10, 8764, 2).
%order_row(10, 1234, 2).%

order_row(11, 5643, 2).
order_row(11, 9864, 3).
order_row(11, 8764, 3).

order_row(12, 2433, 2).
order_row(12, 4532, 1).
order_row(12, 8764, 1).

order_row(13, 3421, 1).
order_row(13, 2314, 2).

order_row(14, 2321, 2).
order_row(14, 4532, 2).
order_row(14, 8764, 6).

order_row(15, 4532, 1).

order_row(16, 5648, 3).
order_row(16, 8764, 2).

order_row(17, 2433, 1).
order_row(17, 2314, 1).
order_row(17, 3421, 2).
order_row(17, 4532, 3).

order_row(18, 3421, 1).
order_row(18, 5643, 2).
order_row(18, 2433, 3).
order_row(18, 9876, 1).

order_row(19, 3421, 4).%
order_row(19, 9876, 4).%
order_row(19, 1234, 1).%
%order_row(19, 5643, 3).%

order_row(20, 2433, 2).
order_row(20, 2321, 1).
order_row(20, 4532, 3).
order_row(20, 9864, 1).

order_row(21, 4532, 4).

order_row(22, 2433, 1).

order_row(23, 5643, 2).
order_row(23, 5648, 3).
order_row(23, 2433, 3).
order_row(23, 4532, 2).

order_row(24, 8764, 4).
order_row(24, 2321, 1).
order_row(24, 2314, 2).
order_row(24, 4532, 1).

order_row(25, 4532, 1).

order_row(26, 2433, 2).
order_row(26, 9864, 3).

order_row(27, 2314, 4).
order_row(27, 9876, 2).
order_row(27, 8764, 1).

order_row(28, 5643, 2).
order_row(28, 5648, 3).
order_row(28, 8764, 5).
order_row(28, 3421, 6).

order_row(29, 5643, 1).
order_row(29, 5648, 1).
order_row(29, 8764, 9).
order_row(29, 3421, 4).

order_row(30, 4532, 1).
order_row(30, 2314, 1).

order_row(31, 8764, 4).%
%order_row(31, 4532, 4).%

order_row(32, 5643, 2).
order_row(32, 2314, 6).

order_row(33, 2314, 9).

order_row(34, 2321, 1).
order_row(34, 9876, 1).


% перевірка, чи даний продукт має даний артикул
product_has_articul(product(Articul,_,_,_,_,_,_),Articul).

% перевірка, чи даний продукт належить до даної категорії
product_has_category(product(_,_,_,_,_,_,CategoryCode),CategoryCode).

% перевірка, чи даний постачальник постачає даний продукт
product_has_provider(ProductCode,ProviderCode):-
    product(ProductCode,_,_,_,_,ProviderCode,_).

% знайти продукт за його артикулом
get_product_by_articul(Articul,product(Articul,N,D,Q,P,E,C)):-
    product(Articul,N,D,Q,P,E,C).

% пошук усіх продуктів
is_product(product(A,N,D,Q,P,E,C)):-
    product(A,N,D,Q,P,E,C).

% перевірка, чи дана категорія має продукт із заданим артикулом
category_has_product(CategoryCode,ProductArticul):-
    product(ProductArticul,_,_,_,_,_,CategoryCode).

% перевірка, чи дане замовлення має продукт із заданим артикулом
order_has_product(OrderCode,ProductArticul):-
    order_row(OrderCode,ProductArticul,_).

% перевірка, чи замовлення має покупця із заданим кодом
order_has_customer(OrderCode,CustomerCode):-
    order(OrderCode,_,CustomerCode,_,_).% ? враховувати статус ?

% перевірка, чи категорія має покупця із заданим кодом
category_has_customer(CategoryCode,CustomerCode):-
    category_has_product(CategoryCode,ProductArticul),
    order_has_product(OrderCode,ProductArticul),
    order_has_customer(OrderCode,CustomerCode).



%-- Запит 1 ----------------------------------------------------------------------------
% 1. Продукти яких категорій були продані тим покупцям (хоча б одному), що й продукти певної категорії
% (з повторами)
categories_h1(CategoryCode,ResCategoryCode):-
    category_has_customer(CategoryCode,Customer), % шукаємо покупців заданої категорії
    category_has_customer(ResCategoryCode,Customer), % залишаємо лише категорії, які мають принаймні 1 спільного покупця із заданою категорією
    CategoryCode =\= ResCategoryCode. % у результат не має потрапити задана категорія, тому ми її відкидаємо

% 1. Продукти яких категорій були продані тим покупцям (хоча б одному), що й продукти певної категорії
% (без повторів)
z01_categories_at_least_one(CategoryCode,ResCategoryCode):-
    distinct(categories_h1(CategoryCode,ResCategoryCode)).

% 1. Продукти яких категорій були продані тим покупцям (хоча б одному), що й продукти певної категорії
% (без повторів у вигляді оператора)
has_at_least_one_common_customer_with_category(ResCategoryCode,CategoryCode):-
    z01_categories_at_least_one(CategoryCode,ResCategoryCode).

% Визначення оператора для запиту 1
:- op(600,xfx,has_at_least_one_common_customer_with_category).


% ----- Призначення 1: пошук категорій, продукти яких були продані тим покупцям (хоча б одному), що й продукти певної категорії
% z01_categories_at_least_one(+CategoryCode,-ResCategoryCode)
% Тести:

% ---------
%?- z01_categories_at_least_one(1,ResCategoryCode).
%ResCategoryCode = 2 ;
%ResCategoryCode = 3 ;
%ResCategoryCode = 4 ;
%ResCategoryCode = 5 ;
%ResCategoryCode = 7 ;
%ResCategoryCode = 8 ;
%false.


%?- ResCategoryCode has_at_least_one_common_customer_with_category 1.
%ResCategoryCode = 2 ;
%ResCategoryCode = 3 ;
%ResCategoryCode = 4 ;
%ResCategoryCode = 5 ;
%ResCategoryCode = 7 ;
%ResCategoryCode = 8 ;
%false.
% ---------


%?- z01_categories_at_least_one(6,ResCategoryCode).
%ResCategoryCode = 4 ;
%ResCategoryCode = 5 ;
%false.


%?- z01_categories_at_least_one(2,ResCategoryCode).
%ResCategoryCode = 1 ;
%ResCategoryCode = 3 ;
%ResCategoryCode = 4 ;
%ResCategoryCode = 5 ;
%ResCategoryCode = 7 ;
%ResCategoryCode = 8 ;
%false.


%?- z01_categories_at_least_one(7,ResCategoryCode).
%ResCategoryCode = 1 ;
%ResCategoryCode = 2 ;
%ResCategoryCode = 4 ;
%ResCategoryCode = 5 ;
%ResCategoryCode = 8.


% ----- Призначення 2: перевірка, чи дві задані категорії мають хоча б 1 спільного покупця
% z01_categories_at_least_one(+CategoryCode,+ResCategoryCode)
% Тести:

% ---------
%?- z01_categories_at_least_one(7,1).
%true ;
%false.

%?- 1 has_at_least_one_common_customer_with_category 7.
%true ;
%false.
% ---------

%?- z01_categories_at_least_one(1,6).
%false.

%?- z01_categories_at_least_one(1,4).
%true ;
%false.

%?- z01_categories_at_least_one(3,5).
%true ;
%false.

% ----- Призначення 3: пошук категорій, продукти яких були продані тим покупцям (хоча б одному), що й продукти заданої категорії
% z01_categories_at_least_one(-CategoryCode,+ResCategoryCode)
% Тести:

% ---------
%?- z01_categories_at_least_one(Code,1).
%Code = 2 ;
%Code = 3 ;
%Code = 4 ;
%Code = 5 ;
%Code = 7 ;
%Code = 8 ;
%false.

%?- 1 has_at_least_one_common_customer_with_category CategoryCode.
%CategoryCode = 2 ;
%CategoryCode = 3 ;
%CategoryCode = 4 ;
%CategoryCode = 5 ;
%CategoryCode = 7 ;
%CategoryCode = 8 ;
%false.

% ---------
%?- z01_categories_at_least_one(Code,6).
%Code = 4 ;
%Code = 5 ;
%false.

%?-  z01_categories_at_least_one(Code,2).
%Code = 1 ;
%Code = 3 ;
%Code = 4 ;
%Code = 5 ;
%Code = 7 ;
%Code = 8 ;
%false.

%?- z01_categories_at_least_one(Code,7).
%Code = 1 ;
%Code = 2 ;
%Code = 4 ;
%Code = 5 ;
%Code = 8.

% ----- Призначення 4: знайти всі пари категорій, які мають спільних покупців
% z01_categories_at_least_one(-CategoryCode,-ResCategoryCode)
% Тести:

%?- z01_categories_at_least_one(Code,ResCategoryCode).
%Code = 1,
%ResCategoryCode = 2 ;
%Code = 1,
%ResCategoryCode = 3 ;
%Code = 1,
%ResCategoryCode = 4 ;
%Code = 1,
%ResCategoryCode = 5 ;
%Code = 1,
%ResCategoryCode = 7 ;
%Code = 1,
%ResCategoryCode = 8 ;
%Code = 2,
%ResCategoryCode = 1 ;
%Code = 2,
%ResCategoryCode = 3 ;
%Code = 2,
%ResCategoryCode = 4 ;
%Code = 2,
%ResCategoryCode = 5 ;
%Code = 2,
%ResCategoryCode = 7 ;
%Code = 2,
%ResCategoryCode = 8 ;
%Code = 3,
%ResCategoryCode = 2 ;
%Code = 3,
%ResCategoryCode = 4 ;
%Code = 3,
%ResCategoryCode = 5 ;
%Code = 3,
%ResCategoryCode = 1 ;
%Code = 4,
%ResCategoryCode = 1 ;
%Code = 4,
%ResCategoryCode = 2 ;
%Code = 4,
%ResCategoryCode = 3 ;
%Code = 4,
%ResCategoryCode = 5 ;
%Code = 4,
%ResCategoryCode = 7 ;
%Code = 4,
%ResCategoryCode = 8 ;
%Code = 4,
%ResCategoryCode = 6 ;
%Code = 5,
%ResCategoryCode = 1 ;
%Code = 5,
%ResCategoryCode = 2 ;
%Code = 5,
%ResCategoryCode = 4 ;
%Code = 5,
%ResCategoryCode = 7 ;
%Code = 5,
%ResCategoryCode = 8 ;
%Code = 5,
%ResCategoryCode = 3 ;
%Code = 5,
%ResCategoryCode = 6 ;
%Code = 6,
%ResCategoryCode = 4 ;
%Code = 6,
%ResCategoryCode = 5 ;
%Code = 7,
%ResCategoryCode = 1 ;
%Code = 7,
%ResCategoryCode = 2 ;
%Code = 7,
%ResCategoryCode = 4 ;
%Code = 7,
%ResCategoryCode = 5 ;
%Code = 7,
%ResCategoryCode = 8 ;
%Code = 8,
%ResCategoryCode = 1 ;
%Code = 8,
%ResCategoryCode = 2 ;
%Code = 8,
%ResCategoryCode = 4 ;
%Code = 8,
%ResCategoryCode = 5 ;
%Code = 8,
%ResCategoryCode = 7 ;
%false.

%?- ResCategoryCode has_at_least_one_common_customer_with_category CategoryCode.
%ResCategoryCode = 2,
%CategoryCode = 1 ;
%ResCategoryCode = 3,
%CategoryCode = 1 ;
%ResCategoryCode = 4,
%CategoryCode = 1 ;
%ResCategoryCode = 5,
%CategoryCode = 1 ;
%ResCategoryCode = 7,
%CategoryCode = 1 ;
%ResCategoryCode = 8,
%CategoryCode = 1 ;
%ResCategoryCode = 1,
%CategoryCode = 2 ;
%ResCategoryCode = 3,
%CategoryCode = 2 ;
%ResCategoryCode = 4,
%CategoryCode = 2 ;
%ResCategoryCode = 5,
%CategoryCode = 2 ;
%ResCategoryCode = 7,
%CategoryCode = 2 ;
%ResCategoryCode = 8,
%CategoryCode = 2 ;
%ResCategoryCode = 2,
%CategoryCode = 3 ;
%ResCategoryCode = 4,
%CategoryCode = 3 ;
%ResCategoryCode = 5,
%CategoryCode = 3 ;
%ResCategoryCode = 1,
%CategoryCode = 3 ;
%ResCategoryCode = 1,
%CategoryCode = 4 ;
%ResCategoryCode = 2,
%CategoryCode = 4 ;
%ResCategoryCode = 3,
%CategoryCode = 4 ;
%ResCategoryCode = 5,
%CategoryCode = 4 ;
%ResCategoryCode = 7,
%CategoryCode = 4 ;
%ResCategoryCode = 8,
%CategoryCode = 4 ;
%ResCategoryCode = 6,
%CategoryCode = 4 ;
%ResCategoryCode = 1,
%CategoryCode = 5 ;
%ResCategoryCode = 2,
%CategoryCode = 5 ;
%ResCategoryCode = 4,
%CategoryCode = 5 ;
%ResCategoryCode = 7,
%CategoryCode = 5 ;
%ResCategoryCode = 8,
%CategoryCode = 5 ;
%ResCategoryCode = 3,
%CategoryCode = 5 ;
%ResCategoryCode = 6,
%CategoryCode = 5 ;
%ResCategoryCode = 4,
%CategoryCode = 6 ;
%ResCategoryCode = 5,
%CategoryCode = 6 ;
%ResCategoryCode = 1,
%CategoryCode = 7 ;
%ResCategoryCode = 2,
%CategoryCode = 7 ;
%ResCategoryCode = 4,
%CategoryCode = 7 ;
%ResCategoryCode = 5,
%CategoryCode = 7 ;
%ResCategoryCode = 8,
%CategoryCode = 7 ;
%ResCategoryCode = 1,
%CategoryCode = 8 ;
%ResCategoryCode = 2,
%CategoryCode = 8 ;
%ResCategoryCode = 4,
%CategoryCode = 8 ;
%ResCategoryCode = 5,
%CategoryCode = 8 ;
%ResCategoryCode = 7,
%CategoryCode = 8 ;
%false.


% --- Запит 3 ----------------------------------------------------------
% перевірка, чи покупець купує товари постачальника з певним кодом
customer_has_provider(CustomerCode,ProviderCode):-
    order_has_customer(OrderCode,CustomerCode),
    order_has_product(OrderCode,ProductCode),
    product_has_provider(ProductCode,ProviderCode).

% перевірка, чи існує постачальник з певним кодом
is_provider_code(ProviderCode):-
    provider(ProviderCode,_,_,_).

% перевірка, чи існує покупець з певним кодом
is_customer_code(CustomerCode):-
    customer(CustomerCode,_,_).

% погані покупці, які не купують товар, який постачається одним з постачальників, які не постачають товари, які купує певний покупець
% (покупці, які не отримують товар якогось із потрібних постачальників (таких, що не постачають певному покупцю))
bad_customers_not_bought_one(CustomerCode,BadCustomerCode):-
    is_provider_code(ProviderCode),
    is_customer_code(BadCustomerCode),
    not(customer_has_provider(CustomerCode,ProviderCode)), % знаходимо постачальників, які не постачають продукти заданому покупцю
    not(customer_has_provider(BadCustomerCode,ProviderCode)). % залишаємо таких покупців, які не купують товар знайдених постачальників

% покупці, які купили товари усіх не тих постачальників, що й певний покупець (виконання вимоги "усі")
% (але, можливо, купили і товари постачальника певного покупця)
customers_all_not_those(CustomerCode,GoodCustomerCode):-
    customer(GoodCustomerCode,_,_),
    is_customer_code(CustomerCode),
    not(bad_customers_not_bought_one(CustomerCode,GoodCustomerCode)). % від усіх покупців віднімаємо таких, які не купують товари хоча б 1 постачальника, який не постачає товари заданому покупцю

% погані покупці, які купили товар хоча б в 1 постачальника, в якого купив товар певний покупець (виконання умови "тільки")
bad_customers_bought_one(CustomerCode,BadCustomerCode):-
    customer_has_provider(CustomerCode,ProviderCode), % знаходимо постачальників даного покупця
    customer_has_provider(BadCustomerCode,ProviderCode). % знаходимо таких покупців, які мають спільного постачальника із заданим покупцем

%3. Знайти покупців, які купили товари усіх не тих і тільки таких постачальників, товари яких купив певний покупець.
z03_customers_only_all_not_those(CustomerCode,GoodCustomerCode):-
    customers_all_not_those(CustomerCode,GoodCustomerCode), % знаходимо покупців, які купили товари усіх не тих постачальників, товари яких купив певний покупець
    not(bad_customers_bought_one(CustomerCode,GoodCustomerCode)). % від знайдних покупців віднімаємо поганих покупців

% 3. Знайти покупців, які купили товари усіх не тих і тільки таких постачальників, товари яких купив певний покупець.
% (у вигляді оператора)
has_only_all_not_those_providers_who_provide_to_customer(GoodCustomerCode,CustomerCode):-
    z03_customers_only_all_not_those(CustomerCode,GoodCustomerCode).

% Визначення оператора для запиту 3
:- op(600,xfx,has_only_all_not_those_providers_who_provide_to_customer).

% ----- Призначення 1: знайти покупців, які купили товари усіх не тих і тільки таких постачальників, товари яких купив певний покупець
% z03_customers_only_all_not_those(+CustomerCode,-GoodCustomerCode) або z03_customers_only_all_not_those(-CustomerCode,+GoodCustomerCode)
% Тести:

% ---------
%?- z03_customers_only_all_not_those('000000006',Customer).
%Customer = '000000002' ;
%Customer = '000000011' ;
%false.

%?- Customer has_only_all_not_those_providers_who_provide_to_customer '000000006'.
%Customer = '000000002' ;
%Customer = '000000011' ;
%false.

% ---------

%?- z03_customers_only_all_not_those('000000011',Customer).
%Customer = '000000006' ;
%false.

%?- z03_customers_only_all_not_those('000000013',Customer).
%Customer = '000000000' ;
%Customer = '000000007' ;
%false.

%?- z03_customers_only_all_not_those('000000000',Customer).
%Customer = '000000013' ;
%false.

%?- z03_customers_only_all_not_those('000000005',Customer).
%false.

% ----- Призначення 2: перевірити, чи заданий покупець GoodCustomerCode купує товари усіх не тих постачальників, що й певний покупець CustomerCode
% z03_customers_only_all_not_those(+CustomerCode,+GoodCustomerCode)
% Тести:

% ---------
%?- z03_customers_only_all_not_those('000000004','000000012').
%true.

%?- '000000012' has_only_all_not_those_providers_who_provide_to_customer '000000004'.
%true.
% ---------

%?- z03_customers_only_all_not_those('000000013','000000000').
%true.

%?- z03_customers_only_all_not_those('000000006','000000002').
%true.

%?- z03_customers_only_all_not_those('000000007','000000001').
%false.

% ----- Призначення 3: знайти пари покупців, де покупці купили товари усіх не тих постачальників, що й один з покупців
% z03_customers_only_all_not_those(-CustomerCode,-GoodCustomerCode)
% Тести:

%?- z03_customers_only_all_not_those(CustomerCode,GoodCustomerCode).
%CustomerCode = '000000013',
%GoodCustomerCode = '000000000' ;
%CustomerCode = '000000006',
%GoodCustomerCode = '000000002' ;
%CustomerCode = '000000012',
%GoodCustomerCode = '000000004' ;
%CustomerCode = '000000002',
%GoodCustomerCode = '000000006' ;
%CustomerCode = '000000011',
%GoodCustomerCode = '000000006' ;
%CustomerCode = '000000013',
%GoodCustomerCode = '000000007' ;
%CustomerCode = '000000010',
%GoodCustomerCode = '000000008' ;
%CustomerCode = '000000008',
%GoodCustomerCode = '000000010' ;
%CustomerCode = '000000006',
%GoodCustomerCode = '000000011' ;
%CustomerCode = '000000004',
%GoodCustomerCode = '000000012' ;
%CustomerCode = '000000000',
%GoodCustomerCode = '000000013' ;
%CustomerCode = '000000007',
%GoodCustomerCode = '000000013' ;
%false.

%?- GoodCustomerCode has_only_all_not_those_providers_who_provide_to_customer CustomerCode.
%GoodCustomerCode = '000000000',
%CustomerCode = '000000013' ;
%GoodCustomerCode = '000000002',
%CustomerCode = '000000006' ;
%GoodCustomerCode = '000000004',
%CustomerCode = '000000012' ;
%GoodCustomerCode = '000000006',
%CustomerCode = '000000002' ;
%GoodCustomerCode = '000000006',
%CustomerCode = '000000011' ;
%GoodCustomerCode = '000000007',
%CustomerCode = '000000013' ;
%GoodCustomerCode = '000000008',
%CustomerCode = '000000010' ;
%GoodCustomerCode = '000000010',
%CustomerCode = '000000008' ;
%GoodCustomerCode = '000000011',
%CustomerCode = '000000006' ;
%GoodCustomerCode = '000000012',
%CustomerCode = '000000004' ;
%GoodCustomerCode = '000000013',
%CustomerCode = '000000000' ;
%GoodCustomerCode = '000000013',
%CustomerCode = '000000007' ;
%false.


% --- Запит 6 ----------------------------------------------------------
% погані категорії, які не мають хоча б 1 такого покупця, який є в даній категорії
bad_categories_not_have_one(CategoryCode,BadCategoryCode):-
    category(BadCategoryCode,_,_),
    category_has_customer(CategoryCode,CustomerCode), % знаходимо покупців даної категорії
    not(category_has_customer(BadCategoryCode,CustomerCode)). % залишаємо лише погані категорії, які не мають хоча б 1 покупця, який є у заданої категорії

% Знайти категорії, товари з яких купувалися усіма тими покупцями, які купували товари з певної категорії
categories_all_those(CategoryCode,GoodCategoryCode):-
    category(CategoryCode,_,_),
    category(GoodCategoryCode,_,_),
    not(bad_categories_not_have_one(CategoryCode,GoodCategoryCode)),% від усіх категорій віднімаємо погані категорії (які не мають покупця)
    CategoryCode =\= GoodCategoryCode.% не додаємо у результат саму задану категорію

% погані категорії, які мають покупця, якого немає в даній категорії
bad_categories_have_one(CategoryCode,BadCategoryCode):-
    category(BadCategoryCode,_,_),
    category_has_customer(BadCategoryCode,CustomerCode), % з усіх категорій обираємо категорії, які мають покупця,
    not(category_has_customer(CategoryCode,CustomerCode)). % якого немає в заданій категорії

% 6. Знайти категорії, товари з яких купувалися усіма тими, і тільки тими покупцями, які купували товари з певної категорії
z06_categories_only_all_those(CategoryCode,GoodCategoryCode):-
    categories_all_those(CategoryCode,GoodCategoryCode), % обираємо категорії, які мають усіх покупців, що й задана категорія
    not(bad_categories_have_one(CategoryCode,GoodCategoryCode)).% і від них віднімаємо категорії, які мають покупців, яких немає в заданій категорії

% 6. Знайти категорії, товари з яких купувалися усіма тими, і тільки тими покупцями, які купували товари з певної категорії
% (у вигляді оператора)
has_only_all_those_customers_who_buy_in_category(GoodCategoryCode,CategoryCode):-
    z06_categories_only_all_those(CategoryCode,GoodCategoryCode).

% Визначення оператора для запиту 6
:- op(600,xfx,has_only_all_those_customers_who_buy_in_category).


% ----- Призначення 1: знайти категорії, товари з яких купувалися усіма тими і тільки тими покупцями, які купували товари з певної категорії
% z06_categories_only_all_those(+CategoryCode,-GoodCategoryCode) або z06_categories_only_all_those(-CategoryCode,+GoodCategoryCode)
% Тести:

%?- z06_categories_only_all_those(7,GoodCategoryCode).
%GoodCategoryCode = 8.

%?- Category has_only_all_those_customers_who_buy_in_category 7.
%Category = 8.

%?- z06_categories_only_all_those(2,GoodCategoryCode).
%false.

% ----- Призначення 2: перевірити, чи певна категорія має всіх тих і тільки тих покупців, що й задана категорія
% z06_categories_only_all_those(+CategoryCode,+GoodCategoryCode)
% Тести:

%?- z06_categories_only_all_those(7,8).
%true.

%?- 8 has_only_all_those_customers_who_buy_in_category 7.
%true.

%?- z06_categories_only_all_those(7,2).
%false.

% ----- Призначення 3: знайти пари категорій, які мають однакових покупців
% z06_categories_only_all_those(-CategoryCode,-GoodCategoryCode)
% Тести:

%?- z06_categories_only_all_those(Category1,Category2).
%Category1 = 7,
%Category2 = 8 ;
%Category1 = 8,
%Category2 = 7 ;
%false.

%?- Category1 has_only_all_those_customers_who_buy_in_category Category2.
%Category1 = 8,
%Category2 = 7 ;
%Category1 = 7,
%Category2 = 8 ;
%false.


% ----------------------------------------------
:-op(300, xfy, plays).
:- op(200, xfy, and).
jimmy plays football and squash.
susan plays tennis and basketball and valleyball.
vasyl plays tennis and basketball and valleyball.

% ----------------------------------------------
% :- op(300, xfy, is_income_from_product).
% 960 is_income_from_product "Embroidery with flowers".

% What is_income_from_product "Acrilic"

% Список кількостей товару з рядків замовлення
list_of_quantities(Articul, Quantity) :- order_row(_, Articul, Quantity).

% Скільки продано товару
quantity_of_sailed_product([],0) :- !.
quantity_of_sailed_product([H|T],SumQuantity) :- quantity_of_sailed_product(T,SumQuantity1),
                                                 SumQuantity is SumQuantity1 + H.

% Чи замовлення всіх статусів рахувати??
% 5. Знайти суму, на яку було продано певного товару, заданого назвою (від початку роботи складу)
% z05_income_from_product(?String, ?Integer)
% Використання:
% 1) За назвою товару ProductName знайти суму Income, на яку було продано цього товару:
% z05_income_from_product(+ProductName, -Income).
% 2) За сумою Income знайти назви товарів ProductName, яких було продано на цю суму:
% z05_income_from_product(-ProductName, +Income).
% 3) Вивести для кожного товару з назвою ProductName суму Income, на яку було продано цього товару:
% z05_income_from_product(-ProductName, -Income).
% 4) Перевірити, що Income - сума, на яку продано товару з назвою ProductName:
% z05_income_from_product(+ProductName, +Income).
z05_income_from_product(ProductName, Income) :- product(Articul, ProductName, _, _, Price, _, _),
                                                bagof(Quantity,list_of_quantities(Articul, Quantity),QuantityList),
                                                quantity_of_sailed_product(QuantityList,SumQuantity),
                                                Income is SumQuantity * Price.
% Приклад 1: z05_income_from_product(+ProductName, -Income)
% ?- z05_income_from_product("Embroidery with flowers", Income).
% Income = 920.
% Приклад 2: z05_income_from_product(-ProductName, +Income)
% ?- z05_income_from_product(ProductName, 310).
% ProductName = "Tape" ;
% Приклад 3: z05_income_from_product(-ProductName, -Income)
% ?- z05_income_from_product(ProductName, Income).
% ProductName = "Embroidery with flowers",
% Income = 920 ;
% ProductName = "Embroidery with cats",
% Income = 700 ;
% ProductName = "White paper",
% Income = 1120 ;
% ProductName = "Acrilic",
% Income = 2200 ;
% ProductName = "Knitting needles",
% Income = 1350 ;
% ProductName = "Knitting threads",
% Income = 760 ;
% ProductName = "Clay",
% Income = 540 ;
% ProductName = "Foamiran",
% Income = 80 ;
% ProductName = "Tape",
% Income = 310 ;
% ProductName = "Glitter",
% Income = 860 ;
% ProductName = "Coloured Paper",
% Income = 88 ;
% ProductName = "Coloured Beads",
% Income = 9 ;
% ProductName = "Wooden decks",
% Income = 97.

% Приклад 4: z05_income_from_product(+ProductName, +Income)
% ?- z05_income_from_product("Tape", 310).
% true.

% Income is_income_from_product z05_income_from_product("Embroidery with flowers", Income).


%------------------------------------------------
% Постачальники, чиї товари продав продавець
providers_of_salesman(SalesmanIpn,Edrpou) :- order(OrderCode, SalesmanIpn, _, _, _),
                                             order_row(OrderCode,Articul,_),
                                             product(Articul,_,_,_,_,Edrpou,_).

% Продавці, які не продавали товари якогось з постачальників, чиї товари продав певний продавець
bad_salesmen(GivenSalesmanIpn,SalesmanIpn) :- providers_of_salesman(GivenSalesmanIpn,Edrpou),
                                              salesman(SalesmanIpn,_),
                                              not(providers_of_salesman(SalesmanIpn,Edrpou)).

% 2. Знайти всіх продавців, які продали товари всіх тих постачальників,
% чиї товари продав певний продавець
% z02_salesmen_all_providers_of_given_salesman(?Integer, ?Integer, ?String)
% Використання:
% 1) За кодом певного продавця GivenSalesmanIpn знайти коди SalesmanIpn та прізвища SalesmanSurname
% всіх продавців, які продали товари всіх тих постачальників, чиї товари продав певний продавець:
% z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,-SalesmanIpn,-SalesmanSurname).
% 2) За кодом продавця SalesmanIpn та прізвищем SalesmanSurname визначити код певного продавця
% GivenSalesmanIpn, який продав товари всіх тих постачальників, чиї товари продав продавець:
% z02_salesmen_all_providers_of_given_salesman(-GivenSalesmanIpn,+SalesmanIpn,+SalesmanSurname).
% 3) За кодом певного продавця GivenSalesmanIpn знайти коди SalesmanIpn всіх продавців, які продали
% товари всіх тих постачальників, чиї товари продав певний продавець:
% z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,-SalesmanIpn,_).
% 4) Перевірити, що продавець з кодом SalesmanIpn та прізвищем SalesmanSurname продав товари всіх тих
% постачальників, чиї товари продав певний продавець з кодом GivenSalesmanIpn:
% z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,+SalesmanIpn,+SalesmanSurname).
z02_salesmen_all_providers_of_given_salesman(GivenSalesmanIpn,SalesmanIpn,SalesmanSurname) :-
                               salesman(GivenSalesmanIpn,_),
                               salesman(SalesmanIpn,full_name(SalesmanSurname,_,_)),
                               not(bad_salesmen(GivenSalesmanIpn,SalesmanIpn)),
                               GivenSalesmanIpn \= SalesmanIpn.
% Приклад 1: z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,-SalesmanIpn,-SalesmanSurname)
% ?- z02_salesmen_all_providers_of_given_salesman('100000000',SalesmanIpn,SalesmanSurname).
% SalesmanIpn = '200000000',
% SalesmanSurname = 'Chernenko' ;
% SalesmanIpn = '300000000',
% SalesmanSurname = 'Voloshyn' ;
% SalesmanIpn = '400000000',
% SalesmanSurname = 'Stepanov' ;
% SalesmanIpn = '500000000',
% SalesmanSurname = 'Fedorova' ;
% SalesmanIpn = '600000000',
% SalesmanSurname = 'Shevchenko' ;
% SalesmanIpn = '700000000',
% SalesmanSurname = 'Romanenko'.
% Приклад 2: z02_salesmen_all_providers_of_given_salesman(-GivenSalesmanIpn,+SalesmanIpn,+SalesmanSurname)
% ?- z02_salesmen_all_providers_of_given_salesman(GivanSalesmanIpn,'400000000','Stepanov').
% GivanSalesmanIpn = '100000000' ;
% GivanSalesmanIpn = '700000000'.
% Приклад 3: z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,-SalesmanIpn,_)
% ?- z02_salesmen_all_providers_of_given_salesman('700000000',SalesmanIpn,_).
% SalesmanIpn = '200000000' ;
% SalesmanIpn = '300000000' ;
% SalesmanIpn = '400000000' ;
% SalesmanIpn = '500000000' ;
% Приклад 4: z02_salesmen_all_providers_of_given_salesman(+GivenSalesmanIpn,+SalesmanIpn,+SalesmanSurname)
% ?- z02_salesmen_all_providers_of_given_salesman('100000000','500000000','Fedorova').
% true.

%------------------------------------------------
% Постачальники, чиї товари купляв покупець
providers_of_customer(CustomerCode,Edrpou) :- order(OrderCode, _, CustomerCode, _, _),
                                              order_row(OrderCode, Articul,_),
                                              product(Articul,_,_,_,_,Edrpou,_).

% Постачальники, чиї товари не купляв покупець
not_providers_of_customer(CustomerCode,Edrpou) :- provider(Edrpou,_,_,_),
                                                  customer(CustomerCode,_,_),
                                                  not(providers_of_customer(CustomerCode,Edrpou)).

% Покупці, які купували товари хоч одного постачальника, чиї товари не купляв певний покупець
bad_customers_at_least_one_provider(GivenCustomerCode,CustomerCode) :-
                                        customer(GivenCustomerCode,_,_),
                                        customer(CustomerCode,_,_),
                                        not_providers_of_customer(GivenCustomerCode,Edrpou),
                                        providers_of_customer(CustomerCode,Edrpou).

% Прізвища покупців, які купували товари хоч одного постачальника, товари якого купував певний покупець
customers_at_least_one_provider(GivenCustomerCode,CustomerCode,CustomerSurname) :-
                                        customer(GivenCustomerCode,_,_),
                                        customer(CustomerCode,full_name(CustomerSurname,_,_),_),
                                        providers_of_customer(GivenCustomerCode,Edrpou),
                                        providers_of_customer(CustomerCode,Edrpou),
                                        CustomerCode \= GivenCustomerCode.

% Покупці, які купували товари тільки тих постачальників, товари яких купував певний покупець (з повторами)
customers_just_providers_of_given_customer(GivenCustomerCode,CustomerCode,CustomerSurname) :-
                       customers_at_least_one_provider(GivenCustomerCode,CustomerCode,CustomerSurname),
                       not(bad_customers_at_least_one_provider(GivenCustomerCode,CustomerCode)).

% 4. Знайти покупців, які купували товари тільки тих постачальників, товари яких купував певний покупець
% z04_customers_just_providers_of_given_customer(?Integer, ?Integer, ?String)
% Використання:
% 1) За кодом певного покупця GivenCustomerCode знайти коди CustomerCode та прізвища CustomerSurname
% всіх покупців, які купували товари тільки тих постачальників, товари яких купував певний покупець:
% z04_customers_just_providers_of_given_customer(+GivenCustomerCode,-CustomerCode,-CustomerSurname).
% 2) За кодом покупця CustomerCode та прізвищем CustomerSurname визначити код певного покупця
% GivenCustomerCode, який купив товари тільки тих постачальників, товари яких купував покупець:
% z04_customers_just_providers_of_given_customer(-GivenCustomerCode,+CustomerCode,+CustomerSurname).
% 3) За кодом певного покупця GivenCustomerCode знайти коди CustomerCode всіх покупців, які
% купували товари тільки тих постачальників, товари яких купував певний покупець:
% z04_customers_just_providers_of_given_customer(+GivenCustomerCode,-CustomerCode,_).
% 4) Перевірити, що покупець з кодом CustomerCode та прізвищем CustomerCode купував товари тільки тих
% постачальників, товари яких купував певний покупець з кодом GivenCustomerCode:
% z04_customers_just_providers_of_given_customer(+GivenCustomerCode,+CustomerCode,+CustomerSurname).
z04_customers_just_providers_of_given_customer(GivenCustomerCode,CustomerCode,CustomerSurname) :-
                       distinct(customers_just_providers_of_given_customer(GivenCustomerCode,CustomerCode,CustomerSurname)).
% Приклад 1: z04_customers_just_providers_of_given_customer(+GivenCustomerCode,-CustomerCode,-CustomerSurname)
% ?- z04_customers_just_providers_of_given_customer('000000003',CustomerCode,CustomerSurname).
% CustomerCode = '000000004',
% CustomerSurname = 'Chaplyk' ;
% CustomerCode = '000000005',
% CustomerSurname = 'Volkov' ;
% CustomerCode = '000000006',
% CustomerSurname = 'Vaha' ;
% CustomerCode = '000000008',
% CustomerSurname = 'Andrushchenko' ;
% CustomerCode = '000000009',
% CustomerSurname = 'Salnikov' ;
% CustomerCode = '000000014',
% CustomerSurname = 'Hryhorenko' ;
% Приклад 2: z04_customers_just_providers_of_given_customer(-GivenCustomerCode,+CustomerCode,+CustomerSurname)
% ?- z04_customers_just_providers_of_given_customer(GivenCustomerCode,'000000014','Hryhorenko').
% GivenCustomerCode = '000000000' ;
% GivenCustomerCode = '000000003' ;
% GivenCustomerCode = '000000005' ;
% GivenCustomerCode = '000000007' ;
% Приклад 3: z04_customers_just_providers_of_given_customer(+GivenCustomerCode,+CustomerCode,_)
% ?- z04_customers_just_providers_of_given_customer('000000004',CustomerCode,_).
% CustomerCode = '000000006' ;
% Приклад 4: z04_customers_just_providers_of_given_customer(+GivenCustomerCode,+CustomerCode,+CustomerSurname)
% ?- z04_customers_just_providers_of_given_customer('000000003','000000014','Hryhorenko').
% true ;