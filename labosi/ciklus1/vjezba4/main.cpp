//
// Created by ivan on 3/22/15.
//

#include <iostream>

class CoolClass{
public:
    virtual void set(int x){x_=x;};
    virtual int get(){return x_;};
    int x_;
    int y_;
};
class PlainOldClass{
public:
    void set(int x){x_=x;};
    int get(){return x_;};
private:
    int x_;
};

int main()
{
    using namespace std;
    CoolClass c;
    cout<<&(c.x_)<<" "<<&(c.y_)<<" "<<&(c)<<" "<<endl;
    cout<<sizeof(PlainOldClass)<<endl;
    return 0;
}