packer {
  required_plugins {
    amazon = {
      version = ">= 1.2.8"
      source  = "github.com/hashicorp/amazon"
    }
  }
}

source "amazon-ebs" "ubuntu" {
  instance_type = "t2.micro"
  region        = "us-west-2"
  source_ami_filter {
    filters = {
      #name                = "ubuntu/images/*ubuntu-jammy-22.04-amd64-server-*"
      name = "ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-20230516"
      root-device-type    = "ebs"
      virtualization-type = "hvm"
    }
    most_recent = true
    owners      = ["099720109477"]
  }
  ssh_username = "ubuntu"
  ami_name = "packer-example-${replace(timestamp(), ":", "_")}"
}

variable "jar_file" {
    type = string 
  default = "api-test-monolith-1.0-SNAPSHOT.jar"
}

variable "systemd_file" {
    type = string 
    default = "build/myapp.service"
}   

build {
  sources = ["source.amazon-ebs.ubuntu"]

  provisioner "shell" {
    inline = [
      #"sudo rm -rf /var/lib/apt/lists/*",
      "sudo apt-get clean",
      "sudo apt-get autoclean",
      "sudo apt-get update",
      "sudo lsb_release -a",
      "cat /etc/apt/sources.list",
      "ls /etc/apt/sources.list.d",
      #"sudo apt-get install -y openjdk-17-jdk git",
      #"sudo apt-get install java-common",
      #"sudo apt-get install -y openjdk-17-jre-headless",
      #"sudo apt-get -y install default-jre",
      #"sudo apt-get -y install maven",
      #"sudo apt-get install -y default-jre",s
      #"sudo apt-get install -y openjdk-17-jdk-headless git",
      "sudo apt-get install -y default-jre git maven",
      "git clone git@github.com:PranoSA/PracticeCICDMaven.git",
      "cd api-test-monolith",
      "mvn package",
      "sudo mv target/${var.jar_file} /opt/",
      "sudo mv ${var.systemd_file} /etc/systemd/system/myapp.service",
      "sudo rm -R src/ pom.xml target/",
      "sudo systemctl enable myapp.service"
    ]
  }
}