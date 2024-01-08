variable "aws_region" {
  description = "AWS region"
  type        = string
}

variable "ami_id" {
  description = "AMI ID created by Packer"
  type        = string
}

variable "eip_alloc_id" {
  description = "EIP allocation ID created by Terraform"
  type        = string
}
