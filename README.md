https://github.com/PranoSA/PracticeCICDMaven/tree/master

Inside Terraform you can find the Terraform scripts
main.tf

It provisions a 
VPC - Virtual Private Network
Subnet Inside the VPC 

A Route Table Associated with this Subnet -> Route Tables Are responsible for the Gateway or Next Hop 

Its Associated With Any of these Cloud Objects (Not Next Hop links)
Internet Gateway (gateway_id): This is used to route traffic from instances in your VPC to the internet or other AWS services.

Virtual Private Gateway: This is used to route traffic from your VPC to your on-premises network over a Direct Connect or VPN connection.

Transit Gateway: This is used to route traffic between VPCs and your on-premises network in a hub-and-spoke model.

VPC Peering Connection: This is used to route traffic between two peered VPCs.

Network Interface: This is used to route traffic to a specific network interface in your VPC.

Instance ID: This is used to route traffic to a specific instance in your VPC.

NAT Gateway: This is used to enable instances in a private subnet to connect to the internet or other AWS services.

Egress-only Internet Gateway: This is used to allow IPv6 traffic from instances in your VPC to the internet, while preventing unsolicited inbound connections.

In this instance, it's to route traffic from the subnet to the public web. The Vice versa is set by AWS. 

A Security Group That I associate With My Web-Server
Security Groups are responsible for either ingress and egress and type of traffic and port for your EC2 Instance.

There's also Network ACLs that attach to Subnets and are stateless, so they don't track connections and a TCP response isn't guaranteed (Security Groups work on the concept of connection so if a connection occurs it can respond) So Packets Not Connections. 

I think Network ACLs are more so applications running on EXPECTED ports (like Postgres or MySQL) can talk to the subnet. 

There's also something called an Elastic IP. Each Elastic IP is allocated with a certain IP address and to associate it with my EC2 instance when it rebuilds (So I can deploy the new application and keep my IP) in the script as well. This is by referencing the resource ID. 

And you can also see my "Security Group Association" and "Route Tabel Assocation" objects that actually match the SG and RT to the instance / subnet respectively. 


Packer -> 
Packer is probably something you can look at and see generally what its doing.
Its using a Base AMI Image (Amazon Machine Image)
Copying some files
Installing a Bunch of Software On It
And Enabling the Daemon the run on start

Jenkins ->
Jenkinsfile is the Jenkins Pipe declaration, you can probably tell generally what the stages are doing, checking out from github, running maven tests, building packer image, and using the output from the packer image to deploy to Jenkins


AWS -> 
AWS Credentials that have Full EC2 Permission are required (EC2 permissions involve everything involved with Non-Serverless Applications Essentially - Like Traditional Software). Machines, Route Tables, Security Groups, Firewalls / ACLs, Subnets, etc. Things with actual IP addresses running on actual configurable hardware (EC2 Instances) and not some service accessed by credentials , name, and URL. 
