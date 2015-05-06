//
// Created by ivan on 4/13/15.
//
#include <vector>
#include <string>
#include <fstream>
#include <ctime>
#include <algorithm>
#include <iostream>
#include <unistd.h>
#include <assert.h>

typedef std::vector<int> Collection;

class Izvor {
protected:
    FILE *stream;
public:
    virtual int getNextNumber() const {
        while (true) {
            int i = getc(stream);
            if (i == EOF)return -1;
            if (isdigit(i)) return i - 48;
        }
    }
};

class TipkovnickiIzvor : public Izvor {
public:
    TipkovnickiIzvor() { stream = stdin; }
};

class DatotecniIzvor : public Izvor {
public:
    DatotecniIzvor(const std::string &fname) {
        stream = fopen(fname.c_str(), "r");
        assert(stream != nullptr);
    }

    virtual ~DatotecniIzvor() {
        fclose(stream);
    }

};

class SlijedBrojeva;

class Observer {
protected:
    const SlijedBrojeva &subject;
public:
    virtual void update() = 0;

    Observer(const SlijedBrojeva &subject) : subject(subject) { }
};


class SlijedBrojeva {
private:
    const Izvor &izvor;
    Collection collection;
    std::vector<Observer *> observers;
public:
    SlijedBrojeva(Izvor const &izvor) : izvor(izvor) { }

    void kreni() {
        while (korak() != -1)
            sleep(1);
    };

    int korak() {
        int num = izvor.getNextNumber();
        if (num != -1) {
            collection.push_back(num);
            notify();
        }
        return num;
    }

    void attach(Observer *observer) { observers.push_back(observer); }

    //void detach(Observer * observer){observers.erase(std::remove(observers.begin(), observers.end(), observer), observers.end());}

    void notify() { for (auto observer : observers) observer->update(); }

    const Collection &getCollection() const {
        return collection;
    }
};


class ObserverTxt : public Observer {
private:
    std::string fname;
public:
    ObserverTxt(const SlijedBrojeva &subject, std::string fname) : Observer(subject), fname(fname) {
    }

    void update() {
        std::ofstream file;
        file.open(fname);

        for (auto &element : subject.getCollection())
            file << element << std::endl;

        auto time = std::time(nullptr);
        file << std::asctime(std::localtime(&time)) << std::endl;
        file.close();
    }

};

class ObserverSuma : public Observer {
public:
    ObserverSuma(const SlijedBrojeva &subject) : Observer(subject) { }

    void update() {
        auto collection = subject.getCollection();
        int sum = std::accumulate(collection.begin(), collection.end(), 0);
        std::cout << "Suma je: " << sum << std::endl;
    }
};

class ObserverProsjek : public Observer {
public:
    ObserverProsjek(const SlijedBrojeva &subject) : Observer(subject) { }

    void update() {
        auto collection = subject.getCollection();
        double prosjek = std::accumulate(collection.begin(), collection.end(), 0) / collection.size();
        std::cout << "Prosjek je: " << prosjek << std::endl;
    }
};

class ObserverMedijan : public Observer {
public:
    ObserverMedijan(const SlijedBrojeva &subject) : Observer(subject) { }

    void update() {
        Collection collection = subject.getCollection();

        std::sort(collection.begin(), collection.end());

        double medijan = collection.at(collection.size() >> 1);
        std::cout << "Medijan je: " << medijan << std::endl;
    }
};

int main() {
    DatotecniIzvor izvor("upis.txt");
    SlijedBrojeva sb(izvor);

    sb.attach(new ObserverSuma(sb));
    sb.attach(new ObserverProsjek(sb));
    sb.attach(new ObserverMedijan(sb));
    sb.attach(new ObserverTxt(sb, "ispis.txt"));

    sb.kreni();

    return 0;
}

