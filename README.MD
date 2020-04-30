Given a lot of pictures
You need to write a program in Java that recognizes which cards are on the table (in the center of the picture). For example, in this picture https://i.gyazo.com/65658f6ab114de07d5c08d5f81324dc7.png on the table are 4hQd7s cards
The program should be able to recognize cards including in new pictures that were not available at the time of writing
Errors in recognition of the order of 5%
You cannot use ready-made libraries for text recognition. You must write your own card recognition algorithm
The program must be provided in a form ready to run on a Windows desktop. The run.cmd file takes the path to the image folder as a parameter. The result is printed to the console in the form of "file name - card" for all files in the folder
To start use out / production / ImageCardReader / CardReader.class file. Example: java CardReader E: \ TempCards Where E: TempCards is a folder with map images.


- Дано множество картинок
- Необходимо написать программу на Java, которая распознает, какие карты лежат на столе (по центру картинки). Например, на этой картинке 
https://i.gyazo.com/65658f6ab114de07d5c08d5f81324dc7.png на столе лежат карты 4hQd7s
- Программа должна уметь распознавать карты в том числе на новых картинках, которые не были доступны на момент написания программы
- Допускаются ошибки в распознавании порядка 5%
- Нельзя использовать готовые библиотеки для распознавания текста. Необходимо написать свой алгоритм распознавания карт
- Программу нужно предоставить в виде, готовом к запуску на Windows десктопе. Файл run.cmd параметром принимает путь до папки с картинками. 
В консоль распечатывается результат в виде "имя файла - карты" для всех файлов папки

Для запуска использовать out/production/ImageCardReader/CardReader.class file.
Example : java CardReader E:\TempCards
Где E:TempCards - папка с изображениями карт. 
 
