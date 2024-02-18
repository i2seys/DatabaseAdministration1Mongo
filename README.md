**Создать базу данных интернет магазина** (тематика на ваш выбор) используя **MongoDB**. 
БД должна хранить информацию о товарах (название, количество, цена, производитель), их категориях (название категории), работниках и клиентах (имена и т.д.) и заказах (статус, дата).
Необходимо заполнить коллекцию демонстрационными данными
**Добавить схеме валидацию**. Определить, какие поля должны содержаться в каждой коллекции и типы данных, которые они должны хранить.
Реализовать следующие запросы к созданной бд:
1.	Получение списка всех категорий
2.	Получение списка всех продуктов в категории
3.	Поиск продукта по названию
4.	Добавление продукта в корзину клиента
5.	Получение списка всех заказов клиента
6.	Обновление статуса заказа
7.	Получение списка топ-продаж за последние месяцы с учетом цены и количества проданных товаров.
8.	Получение списка клиентов, которые сделали более чем N покупок в последнее время.
9.	Получите какие категории товаров пользовались спросом в заданный срок
10.	Какие товары не были проданы в какую-то дату 

Создать следующих пользователей базы данных:
Администратор: имеет полный доступ к базе данных, в том числе может добавлять, редактировать и удалять продукты и пользователей.
Менеджер: имеет доступ к управлению продуктами, но не может управлять пользователями.
Пользователь: может только просматривать продукты и оформлять заказы.
Гость: не имеет доступа к личному кабинету и может только просматривать продукты.
	

**Используя любой язык программирования на ваш выбор, реализовать следующий функционал:**
1.	Подключение к базе данных
2.	Реализовать функционал поиска товаров, включая выбор категории, поиск по ключевым словам и фильтры цены и других характеристик
3.	Реализовать функционал добавления товаров в корзину и подсчета общей стоимости заказа
4.	Реализовать тесты для проверки схемы базы данных. Эти тесты должны проверять, что схема базы данных корректно реализует требования, определенные в первом шаге