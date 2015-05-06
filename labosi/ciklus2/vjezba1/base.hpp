#ifndef _BASE_CLASS
#define _BASE_CLASS

class Base {
public :
    virtual ~ Base() { };

    virtual int solve() = 0;
};

#endif //_BASE_CLASS