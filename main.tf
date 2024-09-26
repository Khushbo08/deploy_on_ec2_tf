provider "aws" {
  region = "us-east-1"
}

# resource "aws_key_pair" "key" {
#   key_name   = "my-key"
#   public_key = file("~/.ssh/id_rsa.pub")
# }

resource "aws_instance" "app_server" {
  ami                    = "ami-0e86e20dae9224db8"  
  instance_type          = "t2.micro"
#   key_name               = aws_key_pair.key.key_name
  associate_public_ip_address = true
  

  user_data = <<-EOF
  #!/bin/bash
  sudo apt update -y
  sudo apt upgrade -y
  sudo apt install -y openjdk-17-jdk
  sudo apt install -y docker.io
  sudo systemctl start docker
  sudo systemctl enable docker
  aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 920726643262.dkr.ecr.us-east-1.amazonaws.com/deploy_on_ec2_tf
  docker pull 920726643262.dkr.ecr.us-east-1.amazonaws.com/deploy_on_ec2_tf:latest
  docker run -d -p 8080:8080 920726643262.dkr.ecr.us-east-1.amazonaws.com/deploy_on_ec2_tf:latest
EOF



  tags = {
    Name = "JavaAppSpotInstance"
  }
}

output "instance_public_ip" {
  value = aws_instance.app_server.public_ip
}
