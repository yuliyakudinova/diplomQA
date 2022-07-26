# План автоматизации тестирования веб-формы сервиса покупки туров интернет-банка
## 1. Перечень автоматизируемых сценариев
### Позитивные сценарии

**1. Покупка тура на вкладке "Купить" с главной страницы сервиса**

1.1. Заполнить форму покупки тура валидными данными дебетовой карты (номер карты: 4444 4444 4444 4441)

*Ожидаемый результат: В правом верхнем углу появляется сообщение "Успешно! Операция одобрена Банком"*

1.2. Покупка тура при оплате картой с валидным номером при недостаточном балансе счета (номер карты: 4444 4444 4444 4442)

*Ожидаемый результат: В правом верхнем углу появляется сообщение "Ошибка! Банк отказал в проведении операции"*

**2. Покупка тура на вкладке "Купить в кредит" с главной страницы сервиса**

1.1. Успешная покупка в кредит при оплате картой с валидным номером и достаточным кредитным лимитом (номер карты: 4444 4444 4444 4441)

*Ожидаемый результат: В правом верхнем углу появляется сообщение "Успешно! Операция одобрена Банком"*

1.2. Отказ в покупке в кредит при оплате картой с валидным номером и недостаточным кредитным лимитом (номер карты: 4444 4444 4444 4442)

*Ожидаемый результат: В правом верхнем углу появляется сообщение "Ошибка! Банк отказал в проведении операции"*

### Негативные сценарии

1. Отправка пустой формы покупки тура (оплата дебетовой картой).

*Ожидаемый результат. Все поля подсвечены красным цветом, сообщения об ошибке: «Неверный формат», «Поле обязательно для заполнения».*

2. Отправка пустой формы покупки тура (оплата кредитной картой).

*Ожидаемый результат. Все поля подсвечены красным цветом, сообщения об ошибке: «Неверный формат», «Поле обязательно для заполнения».*

3. Оплата дебетовой картой с истекшим сроком действия (месяц, предшествующий текущему; текущий год)

*Ожидаемый результат. Поле "Месяц" подсвечено красным, сообщение об ошибке "Неверно указан срок действия карты".*

4. Оплата кредитной картой с истекшим сроком действия (месяц, предшествующий текущему; текущий год)

*Ожидаемый результат. Поле "Месяц" подсвечено красным, сообщение об ошибке "Неверно указан срок действия карты".*

5. Оплата по дебетовой карте с истекшим сроком действия (предыдущий год от текущего)

*Ожидаемый результат. Поле "Год" подсвечено красным, сообщение об ошибке "Истёк срок действия карты".*

6. Оплата кредитной картой с истекшим сроком действия (предыдущий год от текущего)

*Ожидаемый результат. Поле "Год" подсвечено красным, сообщение об ошибке "Истёк срок действия карты".*

7. Валидация поля "Номер карты": пустое поле, ввод букв, ввод некорректных символов, ввод менее 16 цифр, ввод более 16 цифр, ввод только пробелов, ввод несуществующего номера карты.

*Ожидаемый результат. Поле «Номер карты» подсвечивается красным цветом, сообщение об ошибке: «Неверный формат». Ввод некорректных символов недоступен.*

8. Валидация поля "Месяц": пустое поле, ввод букв, ввод некорректных символов, ввод менее 2 цифр, ввод более 2 цифр, ввод цифр не входящих в валидный диапазон (01-12),ввод только пробелов.

*Ожидаемый результат. Поле «Месяц» подсвечивается красным цветом, сообщение об ошибке: «Неверно указан срок действия карты». Ввод некорректных символов недоступен.*

9. Валидация поля "Год": пустое поле, ввод букв, ввод некорректных символов, ввод менее 2 цифр, ввод более 2 цифр, ввод цифр не входящих в валидный диапазон (текущий год + 5 лет), ввод только пробелов.

*Ожидаемый результат.  Поле «Год» подсвечивается красным цветом, сообщение об ошибке: «Неверно указан срок действия карты». Ввод некорректных символов недоступен.*

10. Валидация поля "Владелец": ввод букв на кириллице, некорректные символы, имя из 1 буквы, цифры в имени, ввод только пробелов, ввод только имени, ввод только фамилии.

*Ожидаемый результат. Поле «Владелец» подсвечивается красным цветом, сообщение об ошибке: «Поле обязательно для заполнения». Ввод некорректных символов недоступен.*

11. Валидация поля "CVC/CVV": пустое поле, ввод букв, ввод некорректных символов, ввод менее 3 цифр, ввод более 3 цифр, ввод только пробелов.

*Ожидаемый результат. Поле «CVC/CVV» подсвечивается красным цветом, сообщение об ошибке: «Неверный формат». Ввод некорректных символов недоступен.*

## 2. Перечень используемых инструментов с обоснованием выбора
1. Java 11 
2. IntelliJ IDEA - интегрированная среда разработки ПО
3. Docker - для развертывания виртуальных контейнеров и запуске баз данных 
4. JUnit5 - тестовый фреймворк
5. Gradle - система сборки проекта
6. Faker - система генерации случайных данных для тестов. 
7. Lombok - плагин для создания аннотаций, заменяющих значительное количество однообразных конструкторов, getters/setters и пр.
8. Selenide - фреймворк для автоматизированного тестирования веб-приложений 
9. Allure Report - фреймворк для генерации отчета
10. Git/GitHub- система контроля версий
 
## 3. Перечень и описание возможных рисков при автоматизации
1. Работа с СУБД (MySQL, PostgreSQL) требует дополнительной установки программ
2. Некорректность тестовых данных при работе с Faker, что может привести к искажению  результатов тестирования
 
## 4. Интервальная оценка с учётом рисков (в часах) 
- Разработка плана тестирования - 5 часов;
- Подготовка необходимых инструментов, написание кода автотестов - 83 часа;
- Подготовка отчетов - 12 часов;

## 5. План сдачи работ
- Готовность автотестов - 9 рабочих дней после утверждения плана и разрешения вопросов;
- Результат работы автотестов - документы по итогам тестирования (отчет по итогам тестирования + баг-репорты) - 3 рабочих дня ;
- Подготовка отчёта по автоматизации - 3 рабочих для после проведения всех работ.
