//
// Created by ivan on 4/13/15.
//
#include <vector>
#include <string>
#include <fstream>

typedef std::vector<int> Collection;

class Izvor {
public:
    virtual int getNextNumber() const = 0;
};

class TipkovnickiIzvor : public Izvor {

public:
    virtual int getNextNumber() const {
        return -1;
    }
};

class DatotecniIzvor : public Izvor {

public:
    virtual int getNextNumber() const {
        return -1;
    }
};

class SlijedBrojeva {
public:
    void kreni() {
        while (korak() != -1);
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
public:
    SlijedBrojevaWorker(const Izvor &izvor) : izvor(izvor) { }

    virtual const Izvor &getIzvor() const {
        return izvor;
    }

    virtual void pushToCollection(int num) {
        collection.push_back(num);
    }

    virtual const Collection &getCollection() const {
        return collection;
    }
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
        std::remove(fname.c_str());
    }

    virtual void dumpToFile() {
        std::ofstream file;
        file.open(fname);

        for (auto &element : getCollection())

    }

    virtual void pushToCollection(int num) {
        worker.pushToCollection(num);
        dumpToFile();
    }
};

class SlijedBrojevaDekoratorSuma : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorSuma(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }


};

class SlijedBrojevaDekoratorProsjek : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorProsjek(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }


};

class SlijedBrojevaDekoratorMedijan : public SlijedBrojevaDekorator {
public:
    SlijedBrojevaDekoratorMedijan(SlijedBrojeva &worker) : SlijedBrojevaDekorator(worker) { }


};

int main() {
    return 0;
}

