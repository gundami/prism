#!/bin/bash
export LD_LIBRARY_PATH="/home/container/haproxy/lib"
/home/container/haproxy/haproxy -f /home/container/haproxy/haproxy.cfg -sf
echo "haproxy ok"