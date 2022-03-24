# PrismConsole
Prism是基于Haproxy的Minecraft面板服单端口穿透<br/>
支持一切有特征的tcp协议<br/>
## 目前确定支持的有：<br/>
HTTP(Websocket), SSH, MSTSC, Minecraft, PostgreSQL<br/>

## 结构介绍：<br/>
FRP负责打通隧道部分，Haproxy为核心，用于协议分流<br/>
PrismConsole为交互界面，可以自定义所有的命令，可以用来重载配置文件等。<br/>
更新后无需手动修改文件权限，会自动进行。同时新增socat转发tty，可直接使用终端。<br/>
![avatar](https://th.bing.com/th/id/R.88a24ed26fd9d7bbdf83e9f63abc7028?rik=oLaP7fb2HIgZxw&riu=http%3a%2f%2fgetwallpapers.com%2fwallpaper%2ffull%2f3%2f2%2f9%2f969383-free-dark-side-of-the-moon-wallpaper-3840x2160-for-tablet.jpg&ehk=PW%2bpnCNgLYVHVwY%2feTO40Sq20hTsfgkcnNiYE3p53go%3d&risl=&pid=ImgRaw&r=0)
