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
      name                = "ubuntu/images/*ubuntu-jammy-22.04-amd64-server-*"
      root-device-type    = "ebs"
      virtualization-type = "hvm"
    }
    most_recent = true
    owners      = ["099720109477"]
  }
  ssh_username = "ubuntu"
  ami_name      = "packer-example-${timestamp()}"
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
      "sudo apt-get update",
      "sudo apt-get install -y openjdk-17-jdk openjdk-17-jre git maven",
      "git clone https://github.com:PranoSA/PracticeCICDMaven.git",
      "cd api-test-monolith",
      "mvn package",
      "sudo mv target/${var.jar_file} /opt/",
      "sudo mv ${var.systemd_file} /etc/systemd/system/myapp.service",
      "sudo rm -R src/ pom.xml target/",
      "sudo systemctl enable myapp.service"
    ]
  }
}