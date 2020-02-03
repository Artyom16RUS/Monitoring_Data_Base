[![Build Status](https://travis-ci.org/Artyom16RUS/Monitoring_Data_Base.svg?branch=master)](https://travis-ci.org/Artyom16RUS/Monitoring_Data_Base)  [![codecov](https://codecov.io/gh/Artyom16RUS/Monitoring_Data_Base/branch/master/graph/badge.svg)](https://codecov.io/gh/Artyom16RUS/Monitoring_Data_Base)


### _Monitoring Data Base_

* #### Алгоритм определения изменений схемы данных.


##### Что нужно сделать:
Спроектировать алгоритм (фунцию) определяющую есть ли изменения в СУБД или нет начиная с какого-либо времени или идентификатора.

##### Введение:

Из известных алгоритмов мониторигна изменения базы данных _[keldysh.ru](https://keldysh.ru/papers/2012/prep2012_02.pdf)_ 
и исходя из задания, я реализовал мониторинг на основе триггеров.


_Что умеет делать:_

- SQLRequest - создает исходную таблицу данных users для примера.
- ServiceTrigger - создает отдельную таблицу данных user_log с такими же колонками как в исходной таблице users, и 
допольнительно добавлены колонки operation (где отображется статус произведенной операции) и колонка времени изменения.
В user_log будут вноситься все изменения. Далее можно доставать данные в кэш в указанный промежуток времени.


_Что реализованно:_

База данных SQLite.
Код покрыт JUnit тестами.
Код покрыт нагрузочными BenchMark тестами.
