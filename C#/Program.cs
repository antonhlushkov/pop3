using System;
using System.Collections.Generic;
using System.Threading;

class Program
{
    static void Main(string[] args)
    {
        Program program = new Program();
        program.Starter(4, 100, 10, 6);

        Console.ReadKey();
    }

    // Метод для стартування програми з параметрами
    private void Starter(int storageSize, int itemNumbers, int producers_num, int consumer_num)
    {
        // Створення семафорів для управління доступом до сховища
        Semaphore Access = new Semaphore(1, 1);
        Semaphore Full = new Semaphore(storageSize, storageSize);
        Semaphore Empty = new Semaphore(0, storageSize);

        for (int i = 0; i < consumer_num; i++)
        {
            Thread threadConsumer = new Thread(Consumer);
            threadConsumer.Name = "Consumer " + (i + 1);

            // Розрахунок кількості товарів для одного споживача
            int itemNumbersForOneConsumer = itemNumbers / consumer_num;
            if (i == consumer_num - 1)
            {
                itemNumbersForOneConsumer += itemNumbers % consumer_num;
            }

            // Передача параметрів у потік споживача
            threadConsumer.Start(new object[] { itemNumbersForOneConsumer, Access, Full, Empty });
        }

        for (int i = 0; i < producers_num; i++)
        {
            Thread threadProducer = new Thread(Producer);
            threadProducer.Name = "Producer " + (i + 1);

            // Розрахунок кількості товарів для одного виробника
            int itemNumbersForOneProducer = itemNumbers / producers_num;
            if (i == producers_num - 1)
            {
                itemNumbersForOneProducer += itemNumbers % producers_num;
            }

            // Передача параметрів у потік виробника
            threadProducer.Start(new object[] { itemNumbersForOneProducer, Access, Full, Empty });
        }
    }

    private int num_of_item = 0;
    private List<string> storage = new List<string>();

    // Метод для виробництва товару
    private void Producer(object obj)
    {
        object[] args = (object[])obj;
        int itemNumbers = (int)args[0];
        Semaphore Access = (Semaphore)args[1];
        Semaphore Full = (Semaphore)args[2];
        Semaphore Empty = (Semaphore)args[3];

        for (int i = 0; i < itemNumbers; i++)
        {
            Full.WaitOne();  // Очікування, доки сховище не буде повним
            Access.WaitOne();  // Очікування доступу до сховища

            storage.Add("item " + num_of_item);  // Додання нового товару до сховища з унікальним індексом
            Console.WriteLine(Thread.CurrentThread.Name + " added item " + num_of_item);  // Виведення повідомлення про доданий товар
            num_of_item++;  // Збільшення індексу товару

            Empty.Release();  // Звільнення семафору "порожнє сховище"
            Access.Release();  // Звільнення доступу до сховища
        }
    }

    // Метод для споживання товару
    private void Consumer(object obj)
    {
        object[] args = (object[])obj;
        int itemNumbers = (int)args[0];
        Semaphore Access = (Semaphore)args[1];
        Semaphore Full = (Semaphore)args[2];
        Semaphore Empty = (Semaphore)args[3];

        for (int i = 0; i < itemNumbers; i++)
        {
            Empty.WaitOne();  // Очікування, доки сховище не буде порожнім
            Thread.Sleep(1000);  // Затримка для імітації обробки товару
            Access.WaitOne();  // Очікування доступу до сховища

            string item = storage[0];  // Отримання товару з початку сховища
            storage.RemoveAt(0);  // Видалення товару з сховища
            Console.WriteLine(Thread.CurrentThread.Name + " took " + item);  // Виведення повідомлення про взятий товар

            Full.Release();  // Звільнення семафору "повне сховище"
            Access.Release();  // Звільнення доступу до сховища
        }
    }
}
