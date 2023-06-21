# Installing Haddop on Ubuntu 20.04

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
     -  It is recommended to go on *pseudo-distributed mode* for setting up Hadoop as a new user, which allows each daemon to run as a single java process. Using command `ls`, you can find set of configuration files on hadoop folder:
6. 
