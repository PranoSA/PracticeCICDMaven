pipeline {
    agent any

    environment {
        PACKER_CREDENTIALS_ID = 'aws-credentials'
        PACKER_EXECUTABLE = '/path/to/packer' // Optional if Packer is in the PATH
    }

    // 

    stages {

        stage("Unit Test Maven Build"){
            steps{
                sh "mvn test"
            }
        }

        //Integration Test Later -> For Now Just Spin up and Down Postgres

        stage('Build AMI with Packer') {
            steps {
                build {
                    // Run Packer build and capture output
                    def packerOutput = sh(script: "packer build packer.json", returnStdout: true).trim()
                    // Extract AMI ID from Packer output
                    def amiId = packerOutput.readLines().find { it =~ /AMI: (ami-.*)/ }?.replaceAll(/.*AMI: (ami-.*)/, '$1')
                    env.AMI_ID = amiId
                }
            }
        }

        /*stage('Build and Run Docker Image') {
            steps {
                script {
                    // Build Docker image
                    docker.build('my-docker-image')

                    // Run Docker container
                    docker.image('my-docker-image').run('-p 8080:8080', '--name my-container')

                    // Execute some commands inside the Docker container (example)
                    docker.image('my-docker-image').inside {
                        sh 'echo "Hello from Docker container!"'
                    }
                }
            }*/

        /*
        stage('E2E Test') {
            when {
                // Run only for tag events on the master branch
                expression { env.BRANCH_NAME == 'master' && env.CHANGE_ID.endsWith("refs/tags/") }
            }
            steps {
                script {
                    // Start your Spring Boot application
                    sh "java -jar target/your-application.jar &"

                    // Wait for the application to be ready (adjust as needed)
                    sh 'sleep 30'

                    // Run Newman test against the running application
                    sh "newman run path/to/your/postman/collection.json -e path/to/your/environment.json"

                    // Stop the Spring Boot application
                    sh 'kill $(lsof -t -i:8080)'
                }
            }
        }
         */


        stage('Deploy Infrastructure with Terraform') {
            steps {
                script {
                    // Terraform plugin steps
                    terraformCLI(
                        credentialsId: env.TF_CREDENTIALS_ID,
                        workingDirectory: 'terraform',
                        command: 'init'
                    )

                    terraformCLI(
                        credentialsId: env.TF_CREDENTIALS_ID,
                        workingDirectory: 'terraform',
                        command: 'apply',
                        options: [
                            '-auto-approve',
                            "-var=aws_region=${env.AWS_REGION}",
                            "-var=ami_id=${env.AMI_ID}" // Use the AMI created by Packer
                        ]
                    )
                }
            }
        }

}
    post {
        always {
            // Clean up - remove the Docker container
            script {
                docker.image('my-docker-image').stop()
                docker.image('my-docker-image').remove()
            }
        }
    }
}

