[决策表的语法1.png]: https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTcxMjE5MjIxNzAyMDIy?x-oss-process=image/format,png 
[决策表的语法2.png]: https://imgconvert.csdnimg.cn/aHR0cDovL2ltZy5ibG9nLmNzZG4ubmV0LzIwMTcxMjE5MjIxNzI3NDMx?x-oss-process=image/format,png

# 决策表
```
优点：
    1、简化系统架构，优化应用
    2、提高系统的可维护性和维护成本
    3、方便系统的整合
    4、减少编写“硬代码”业务规则的成本和风险
```
# 决策表的语法
__决策表部分关键字__

|关键字|是否必填|值|说明|
|---|---|---|---|
|RuleSet|是|String|在这个单元格右侧包含RuleSet的名称，与drl文件中的package一样的|
|Sequential|否|true/false|true则代表表格从上到下顺序执行，false 代表乱序执行|
|Import|否|String|导入所需的引用的类或方法，多个类用逗号隔开|
|Functions|否|String|功能与标准的drl 文件中函数相同，多个函数在每个函数后用逗号隔开，有无返回值都可以|
|Variables|否|String|全局变量，多个变量用逗号隔开|
|Queries|否|String|定义查询函数，多个用逗号隔开|
|RuleTable|是|String|表示规则名称，在RuleTable 后面直接写规则名称，不要另起一列，规则名以逗号隔开|

__规则部分关键字__

|关键字|是否必填|值|说明|
|:---|:---|:---|:---|
|CONDITION|是|String|指明该列为判断条件，相当于drl文件中的when 部分，每个规则至少有一个CONDITION|
|ACTION|是|String|指明该列为结果，每行如果符合前面的所有CONDITION，既执行此行的ACTION，相当于drl文件中的then|
|PRIORITY|否|int|指明该列的值将被设置为该规则的‘lalience’(规则执行先后顺序，值越大执行顺序越靠前)，但注意，若在ruleSet 下设置了sequential 为true,则PRIORITY不生效，设置为false或不设置，则PRIORITY生效|
|NAME|否|String|指明该列的值将会被设置为从那行产生的规则名称|
|NO-LOOP|否|true/false|指明这个规则不允许循环，与drl文件中no-loop功能一样|
|ACTIOVATION-GROUP|否|String|在这个列中单元格的值，指出该规则行属于特定的活动分组。一个活动分组以为这在命名组中的规则只有一条会被引用（首条规则触发哦，中止其他规则活动）与drl中含义一样|
|AGENDA-GROUP|否|String|在这个列中单元格的值，指出该规则行属于特定的议程组，可以理解成获取焦点（这是一种在规则组之间控制流的方法），与drl文件中含义一样|
|RULEFLOW-GROUP|否|String|在这个列中单元格的值，指出该规则行属于特定的规则流组|

# 遇到的疑问
__1. 执行顺序__
> 默认从上至下，每个都执行一边
```
是否可以修改“执行顺序”？
    使用Sequential关键字，设置为false。则是乱序
第一次执行成功是否可以直接退出？
    kieSession.fireAllRules(1)
```
__2. CONDITION之间是AND还是OR__
> 默认AND
```
在决策表中是否可以修改CONDITION之间的关系？
```