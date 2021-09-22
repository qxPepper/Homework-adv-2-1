public class Seller {
    private final static int NUMBER_OF_ATTEMPTS = 3;
    private final static int TIME_SLEEP = 1000;
    private final static int MAX_COUNT_CAR = 10;

    private boolean availableCar;

    //Завод выпустит 15 автомобилей.
    //До автосалона доедут 10 автомобилей.
    //Покупателей тоже 10.
    //Дадим им по 3 попытки. Кто-то купит больше 1-й машины, а кто-то ничего.
    //Всё воля случая и OC.

    public synchronized void sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " решил купить автомобиль.");

            wait();

            if (availableCar) {
                for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
                    if (Main.countCar == MAX_COUNT_CAR) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + " зашёл в автосалон.");

                    if (!Main.availableCar) {
                        System.out.println(Thread.currentThread().getName() + " - Машин нет. Ждите когда привезут.");
                        Thread.sleep(TIME_SLEEP);

                    } else {
                        Thread.sleep(TIME_SLEEP);
                        System.out.println("Производитель Tesla выпустил 1 авто.");
                        System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто!");
                        Main.availableCar = false;
                        Main.countCar++;
                        Thread.sleep(TIME_SLEEP);
                    }
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    //Здесь даём старт продаж.
    public synchronized void receiveCar() {
        try {
            while (!Main.availableCar) {
                Thread.sleep(TIME_SLEEP);
            }
            availableCar = true;

            notifyAll();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}

