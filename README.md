# 设计  
1. Period代表着一个小时，将一个羽毛球场的一天会将09-22分成13个阶段。每个阶段一个小时。每个阶段可以被Order属于，意味着被Order预定了。
其中period字段在开始时间段设置，为预定的时长。根据该值即可定位到一次预定的时长。  
2. Order类表示一个预订者。用户名相同，则Order相等。  
3. Command类代表一个合法的命令，包括日期、起始时间、结束时间、是否取消、价钱
3. BadmintonField代表一个羽毛球场地，内部一个Map键值为Date，值为一个Period列表；List维持合法的Command，包含预定和取消的命令。  
其中两个方法orderField和cancelField通过Map中的Period列表来判断是否合法，如果合法，则会加入Command的列表中；对于取消命令，还需要将之前预定的命令删除掉。  
4. 关于结果输出。每个BadmintonField负责自己场地的打印。通过将Command的列表排序后，再遍历，打印出结果即可。  
# 测试案例  
test/包下CostTest测试了工作日和周末各个时间段的价格计算。  
case下代表了一些案例  
# 关于运行  
从命令行输入输出


# 关于题目  
在pdf中
