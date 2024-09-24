provider "aws" {
  region = "us-east-1"
}

resource "aws_key_pair" "key" {
  key_name   = "my-key"
  public_key = file("~/.ssh/id_rsa.pub")
}

resource "aws_instance" "app_server" {
  ami                    = "ami-0e86e20dae9224db8"  
  instance_type          = "t2.micro"
  key_name               = aws_key_pair.key.key_name
  associate_public_ip_address = true
  spot_price = "0.01"

  user_data = <<-EOF
  #!/bin/bash
  sudo apt update -y
  sudo apt upgrade -y
  sudo apt install -y openjdk-17-jdk
  sudo apt install -y docker.io
  sudo systemctl start docker
  sudo systemctl enable docker
  docker login -u "${dockerhub_username}" -p "${dockerhub_token}"
  docker pull ${dockerhub_username}/application_img_argocd:latest
  docker run -d -p 8080:8080 ${dockerhub_username}/application_img_argocd:latest
EOF


  tags = {
    Name = "JavaAppSpotInstance"
  }
}

output "instance_public_ip" {
  value = aws_instance.app_server.public_ip
}
