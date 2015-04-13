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

class SlijedBrojeva {
public:
    virtual void kreni() {
        while (korak() != -1)
            sleep(1);
    };

    virtual const Izvor &getIzvor() const = 0;

    virtual void pushToCollection(int num) = 0;

    virtual const Collection &getCollection() const = 0;

    int korak() {
        int num = getIzvor().getNextNumber();
        if (num != -1) pushToCollection(num);
        return num;
    }
};

class SlijedBrojevaWorker : public SlijedBrojeva {
private:
    Collection collection;
    const Izvor &izvor;
protected:
    virtual const Izvor &getIzvor() const {
        return izvor;
    }

    virtual void pushToCollection(int num) {
        collection.push_back(num);
    }

    virtual const Collection &getCollection() const {
        return collection;
    }

public:
    SlijedBrojevaWorker(const Izvor &izvor) : izvor(izvor) { }
};

class SlijedBrojevaDekorator : public SlijedBrojeva {
protected:
    SlijedBrojeva &worker;
public:
    SlijedBrojevaDekorator(SlijedBrojeva &worker) : worker(worker) { }

    virtual const Izvor &getIzvor() const {
        return worker.getIzvor();
    }

    virtual const Collection &getCollection() const {
        return worker.getCollection();
    }
};

class SlijedBrojevaDekoratorTxt : public SlijedBrojevaDekorator {
private:
    std::string fname;
public:
    SlijedBrojevaDekoratorTxt(SlijedBrojeva &worker, std::string fname) : SlijedBrojevaDekorator(worker), fname(fname) {
    }

    void dumpToFile() {

        std::ofstream file;
        file.open(fname);

        for (auto &element : getCollection())
            file << element << std::endl;

        auto time = std::time(nullptr);
        file << std::asctime(std::localtime(&time)) << std::endl;
        file.close();
    }

    virtual void pushToCollection(int num) {
        worker.pushToCollection(num);
        dumpToFile();
    }
};

class SlijedBrojevaDekoratorSuma : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorSuma(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }

    void printSum() {
        auto collection = getCollection();
        int sum = std::accumulate(collection.begin(), collection.end(), 0);
        std::cout << "Suma je: " << sum << std::endl;
    }

    virtual void pushToCollection(int num) {
        worker.pushToCollection(num);
        printSum();
    }
};

class SlijedBrojevaDekoratorProsjek : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorProsjek(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }

    void printProsjek() {
        auto collection = getCollection();
        double prosjek = std::accumulate(collection.begin(), collection.end(), 0) / collection.size();
        std::cout << "Prosjek je: " << prosjek << std::endl;
    }

    virtual void pushToCollection(int num) {
        worker.pushToCollection(num);
        printProsjek();
    }
};

class SlijedBrojevaDekoratorMedijan : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorMedijan(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }

    void printMedijan() {
        Collection collection = getCollection();

        std::sort(collection.begin(), collection.end());

        double medijan = collection.at(collection.size() >> 1);
        std::cout << "Medijan je: " << medijan << std::endl;
    }

    virtual void pushToCollection(int num) {
        worker.pushToCollection(num);
        printMedijan();
    }
};

int main() {
    DatotecniIzvor izvor("upis.txt");
    SlijedBrojevaWorker worker(izvor);
    SlijedBrojevaDekoratorSuma suma(worker);
    SlijedBrojevaDekoratorProsjek prosjek(suma);
    SlijedBrojevaDekoratorMedijan medijan(prosjek);
    SlijedBrojevaDekoratorTxt txt(medijan, "ispis.txt");

    txt.kreni();

    return 0;
}

