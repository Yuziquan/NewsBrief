## NewsBrief

[![state](https://img.shields.io/badge/state-in%20development%20-brightgreen.svg)](https://github.com/Yuziquan/NewsBrief)



An app that helps you quickly browse news and information~~

![NewsBrief](./Screenshots/app_icon.png)



**Minimum compatible version**：

> Android 4.0



[toc]

### 1. TensorFlow 概述

#### 1.1 背景
$\quad$ **TensorFlow 1.8** 版本于2018年3月30日发布，该版本为具有里程碑式意义的版本，引入了很多新功能和新特性。其中必须提到的是这次版本中正式引入的 **Eager Execution** 特性（属于**动态图机制**），这一特性其实早在2017年秋就已经开始进行测试了。

$\quad$ 同时，这一特性，也成为了入门 TensorFlow 的**官方推荐模式**：
> The easiest way to get started with TensorFlow is using Eager Execution.

$\quad$ 在此之前，TensorFlow一直基于传统的 **Graph Execution** 特性（属于**静态图机制**），但该特性有很多缺点，早已被众多开发者所诟病：
* 入门门槛高
* 调试困难
* 灵活性差
* 无法使用 Python 原生控制语句
* ......

$\quad$ 一些新的基于**动态图机制**的深度学习框架（如 PyTorch）也横空出世，并以其易用性和快速开发的特性占据了一席之地。尤其是在像学术研究等需要**快速迭代模型**的领域，PyTorch 等新兴深度学习框架已经成为主流，所以我们知道 PyTorch 经常被用于学术界，而 TensorFlow 经常被用于工业界。

#### 1.2 不同群体学习 TensorFlow 的目的

##### (1) 学生和研究人员：模型的建立与训练
$\quad$ 如果你是一位学生或研究人员，你已知道机器学习算法的相关理论知识，但是不知道这些算法在计算机中具体要如何实现。此时，你希望能有一个程序库，可以帮助你把书本上的理论知识（数学公式和算法）运用于实践中。

$\quad$ 具体而言，以最常见的有监督学习（Supervised Learning）为例。假设你已经掌握了一个模型 $\hat{y} = f(x, \theta)$（其中 $x$，$\hat{y}$ 为真实输入和预测输出，$\theta$ 为模型参数），确定了一个损失函数 $L(y, \hat{y})$（其中 $y$ 为真实输出），并获得了一批数据 $X$ 和相应的标签 $Y$。此时，你会希望能有一个程序库，帮你实现下面这些事情：
* 用计算机程序表示出向量、矩阵和张量等数学概念，并方便地进行运算；
* 方便地建立模型 $\hat{y} = f(x, \theta)$ 和损失函数 $L(y, \hat{y}) = L(y, f(x, \theta))$。使得给定了输入 $x_0 \in X$、对应的标签 $y_0 \in Y$ 和当前迭代轮的参数值 $\theta_0$，能够方便地计算出模型预测值 $\hat{y_0} = f(x_0, \theta_0)$，并计算损失函数的值 $L_0 = L(y_0, \hat{y_0}) = L(y_0, f(x_0, \theta_0))$；
* 已知 $x_0$、$y_0$、$\theta_0$ 时，能够自动求出损失函数 $L$ 对模型参数 $\theta$ 的偏导数值，即计算 $\theta_0' = \left. \dfrac{\partial L}{\partial \theta} \right|_{x=x_0, y=y_0, \theta = \theta_0}$，无需人工推导求导结果（这意味着，这个程序库需要支持某种意义上的“符号运算”，表现在能够记录下运算的全过程，这样才能根据链式法则进行反向求导）；
* 根据求出的偏导数 $\theta_0'$ 的值，方便地调用一些优化方法更新当前迭代轮的模型参数 $\theta_0$，得到下一迭代轮的模型参数 $\theta_1$（比如梯度下降法，$\theta_1 = \theta_0 - \alpha \theta_0'$，$\alpha$ 为学习率）。

总的来说，这个程序库需要能做到：
* 数学概念和数学运算的程序化表达；
* 对任意可导函数 $f(x)$，求在自变量 $$


##### (2) 开发人员和工程师：模型的部署与调用


#### 1.3 TensorFlow 能提供哪些解决方案

##### (1) 

##### (2)

##### (3)