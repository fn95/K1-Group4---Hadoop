# Installing Hadoop on Ubuntu 20.04

## This Github
> **Group 4 Members:**
> Zulfikar Hadzalic			        2106636224
> Fayza Nirwasita			          2106635700
> Rafi' Noval Hady		        	2106703153
> Muhammad Zaki Nur Said Hanan	2106733856

## Installation Steps (on Virtual Machine Box)
> This is a tutorial on installation and set up Hadoop on Ubuntu 20.04 through Virtual Machine Box
> Before going through, first, we set up for Ubuntu 20.04 (Focal Fossa) on your VM Box:
+ Download and Install [Oracle VM VirtualBox](https://www.oracle.com/id/virtualization/technologies/vm/downloads/virtualbox-downloads.html) or other VM Box files.
+ Download the installation media  : you can search the version on [Ubuntu website](https://releases.ubuntu.com/focal/) or other sources to download **.iso** file
+ On your Virtual Machine Box, to create a Virtual Machine, click on **New** button on VirtualBox. Specify name, type, and version for Virtual Machine and click on **Next**
+ Specify the settings from base memory, Virtual Hard Disk, then load Ubuntu ISO file to the Virtual Machine.
+ Start Installing Ubuntu. For detail tutorial, you can refer to [here](https://www.ktexperts.com/how-to-install-ubuntu-20-04-1-lts-on-windows-using-virtualbox/)
  
> Now we are going to set up installation of Hadoop
1. **Create user for Hadoop environment**
   > Open terminal `Ctrl + alt + T` and type following command
   > For precautions, make sure that the command is as a root user and all systems are up-to-date. Command sudo `apt-get update` and sudo 'apt-get upgrade'
3. **Installing Java**
   > Hadooop frameweork requre compatible Java Runtime Environment (JRE) and Java Development Kit (JDK). Hadoop is best at use on Java version 8. Install the commands below to install java version and verify the version installed. 
```
sudo apt update
sudo apt install openjdk-8-jdk -y
java -version
```
3. **Install OpenSSH on Ubuntu**
   > Install the OpenSSH server and client, then switch to created user and generate public and private key pairs. Then verify the SSH.
```
sudo apt install openssh-server openssh-client -y
sudo su - hadoop
$ ssh-keygen -t rsa
$ sudo cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ sudo chmod 640 ~/.ssh/authorized_keys
$ ssh localhost
```
4. **Install Apache Hadoop**
  > You can go to [Apache Hadoop website](https://hadoop.apache.org/releases.html) to download latest version of Hadoop.Then in following command, extract the file. Then, for installed Java, in Hadoop set up the environment variables.
```
$ tar -xvzf hadoop-3.3.2.tar.gz
mv hadoop-[you can edit hadoop file name]
$ which java
dirname $(dirname $(readlink -f $(which java)))
```
5. **Configure Hadoop**
   -  It is recommended to go on *pseudo-distributed mode* for setting up Hadoop as a new user, which allows each daemon to run as a single java process. Using command `ls`, you can find set of configuration files on hadoop folder:
      > bashrc, hadoop-env.sh, core-site.xml, hdfs-site.xml, mapred-site-xml, yarn-site.xml
   -  To configure Hadoop environment variables, go to one of the configuration file then add the following lines, where you can add it on bottom of the file or below `# for examples`
     ```
     $ sudo nano ~/.bashrc
     ```
     > adding following lines:
     ```
     export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
     export HADOOP_HOME=/usr/local/hadoop
     export HADOOP_INSTALL=$HADOOP_HOME
     export HADOOP_MAPRED_HOME=$HADOOP_HOME
     export HADOOP_COMMON_HOME=$HADOOP_HOME
     export HADOOP_HDFS_HOME=$HADOOP_HOME
     export YARN_HOME=$HADOOP_HOME
     export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
     export PATH=$PATH:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
     export HADOOP_OPTS="-Djava.library.path=$HADOOP_HOME/lib/native"
     ```
      > activate the environment variable
     ```
     $ source ~/.bashrc
     ```
     -  Go to access the **hadoop-env.sh** file, then uncomment the `$HADOOP_HOME` variable by removing **#** sign. 
     ```
     sudo nano $HADOOP_HOME/etc/hadoop/hadoop-env.sh
     ```
    > After uncommment, place the path of location of Java Installation, by copying the path from the directory files. Following line is example of finding Java path, then use the ooutput to find OpenJDK directory. path of Java Installation:
     ```
     which javac
     readlink -f /usr/bin/javac
     export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
     ```
6. **Edit core-site.xml File**
   ```
   sudo nano $HADOOP_HOME/etc/hadoop/core-site.xml
   ```
   > In **core-site.xml** file, go into the file in text editor and override the configuration values for temporary directory, then add HDFS URL to replace. Place the following lines (below <! -- .... --> )
   ```
   <configuration>
   <property>
                 <name>fs.defaultFS</name>
                 <value>hdfs://localhost:9000</value>
         </property>
   </configuration>
   ```
7. **Edit  hdfs-site.xml File**
   ```
   sudo nano $HADOOP_HOME/etc/hadoop/hdfs-site.xml
   ```
   >  adjust the NameNode and DataNode directories to your custom locations (below <! -- .... --> )
   ```
    <configuration>
        <property>
                <name>dfs.replication</name>
                <value>1</value>
        </property>
        <property>
                <name>dfs.name.dir</name>
                <value>file:///home/hadoop/hadoopdata/hdfs/namenode</value>
        </property>
        <property>
                <name>dfs.data.dir</name>
                <value>file:///home/hadoop/hadoopdata/hdfs/datanode</value>
        </property>
   </configuration>
   ```

8. **Edit mapred-site.xml File**
   ```
   sudo nano $HADOOP_HOME/etc/hadoop/mapred-site.xml
   ```
   >  achange the default MapReduce framework name value to yarn (below <! -- .... --> )
   ```
   <configuration>
       <property>
              <name>mapreduce.framework.name</name>
              < value>yarn</value>
       </property>
   </configuration>
   ```
9. **Edit yarn-site.xml File**
    ```
    sudo nano $HADOOP_HOME/etc/hadoop/yarn-site.xml
    ```
   >  Append the following configuration to the file (below <! -- .... --> )
   ```
   <configuration>
        <property>
                <name>yarn.nodemanager.aux-services</name>
                <value>mapreduce_shuffle</value>
        </property>
   </configuration>
   ```  
10. **Format HDFS NameNode**
```
hdfs namenode -format
```
11. **Start Hadoop Cluster**
    > It should be containing all the HDFS and YARN daemons (around six lines) after input command `jps`
```
hdfs namenode -format
$ start-yarn.sh
$ jps
```
12. **Access Hadoop UI from Browser**
    - port **9870** is a port number that access Hadoop UI
    - port **9864** is a port number that access individual DataNodes directly from the browser
    - port **8088** is a port number that accessYARN Resource Manager
      ```
      http://localhost:9870
      http://localhost:9864
      http://localhost:9864
      ```
