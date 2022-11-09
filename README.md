# EquivalenceJudge-Toy

Nanjing University Software Engineering course project, Fall 2022

## 这是什么？

该项目的目标是完成一个自动化的程序等价性判断程序。输入为若干C++/C程序的源代码文件，输出为这些程序之间的等价关系。

项目的短期目标为通过编译程序文件、动态执行比较输入输出的方法来判断等价性。事实上，当前阶段该项目更接近于一个OnlineJudge程序，不具备更高级的功能。

总而言之，正如项目名称所示，这是一个玩具性质的等价性判断工具。

## 程序的使用方式

编译并运行项目，按照程序提示输入要进行等价判断的程序文件存放的路径，该路径中应当包括若干文件夹，每个文件夹中有若干C++/C程序的源文件以及一个记录了程序输入格式的.txt文件。如下所示，

```text
input
└─dir
    ├─oj1.cpp
    ├─oj2.cpp
    ├─oj3.cpp
    └─stdin_format.txt
```

之后，程序将会进行等价性判断并将结果输出到output目录下的两个`csv`文件中，如下所示，

```text
output
├─equal.csv     //等价的程序对
└─inequal.csv   //不等价的程序对
```

## 当前进度

代码的基础结构框架已经被搭建