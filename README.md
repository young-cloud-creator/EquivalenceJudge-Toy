# EquivalenceJudge-Toy

Nanjing University Software Engineering course project, Fall 2022

## 这是什么？

该项目的目标是完成一个自动化的程序等价性判断程序。输入为若干C++/C程序的源代码文件，输出为这些程序之间的等价关系。

项目的短期目标为通过编译程序文件、动态执行比较输入输出的方法来判断等价性。事实上，当前阶段该项目更接近于一个OnlineJudge程序，不具备更高级的功能。

总而言之，正如项目名称所示，这是一个玩具性质的等价性判断工具。

## 运行环境

项目在`java 17.0.4.1 2022-08-18 LTS`、`macOS 13.0.1`下能够正常编译和运行。项目并未使用Java 8以后的新特性或者macOS特有的接口，因此理论上在其他版本的JDK和类Unix系统中也能够正常编译和运行。

项目依赖于g++，请确保运行环境中能够正常运行g++。项目依赖于系统的`$SHELL`环境变量所指定的shell程序，请确保系统的`$SHELL`环境变量被正确配置。

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

之后，程序将会进行等价性判断并将结果输出到存放程序文件的目录下的output文件夹中的两个`csv`文件，如下所示，

```text
input
└─dir
    ├─oj1.cpp
    ├─oj2.cpp
    ├─oj3.cpp
    ├─stdin_format.txt
    └─output
        ├─equal.csv     //等价的程序对
        └─inequal.csv   //不等价的程序对
```
