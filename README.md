# ChurchConnect API [![Build Status](https://travis-ci.org/churchconnect/rest-api.svg?branch=master)](https://travis-ci.org/churchconnect/rest-api)

## Development

* Software requirements (most easily installed using [SDKMAN](http://sdkman.io/)):
    * JAVA 8
    * Grails (see `gradle.properties` for the version to install).
* Clone the API. [Repo link](https://bitbucket.org/sharptop/church-connect-api). 
* customize the Contentful configuration in `application.yml`:
    * the `spaceID` can be found by looking at the url when logged into contentful and working in your space.
    * the read apiURL will have the format: `https://cdn.contentful.com/spaces/{spaceID}`
    * the write apiURL will have the format: `https://api.contentful.com/spaces/{spaceID}`
    * create a read api key by going th the "APIs" tab of your contentful space and adding an API key.
    * create a write api key by going to https://www.contentful.com/developers/docs/references/authentication/#the-management-api 
        * Click on the "Login to get a token" button
        * Authorize the application
        * Copy the key that is generated.
        
Below is a sample of how the configs will look in the `application.yml` file:    

```yaml
environments:
    development:
        church:
             contentful:
                 read:
                     apiURL: https://cdn.contentful.com/spaces/xxxxxxxxxxxx
                     apiKey: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                 write:
                     apiURL: https://api.contentful.com/spaces/xxxxxxxxxxxx
                     apiKey: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

* Once configured, run the API with `grails run`

## Building and deploying:

We deploy the API to an AWS Elastic Beanstalk environment, configured as follows:

* Tomcat application
* build your own war file or download one from the build page. See the section below on getting a WAR file.
* choose a `t2.micro` instance type
* In software configuration:
    * Max JVM Heap: 768m 
    * Max permanent generation: 128m
* Set up environment variables for the contentful connections (see above for how to get these values):
    * church.contentful.read.apiKey
    * church.contentful.read.apiURL
    * church.contentful.write.apiKey
    * church.contentful.write.apiURL
* You can verify that the API is running by going to `/metadata` on the application.

## Getting a WAR file

Successful builds of this open source project are uploaded to [this s3 bucket](http://churchconnect-builds.s3.amazonaws.com). Stable releases are listed under the `releases` directory. Other builds triggered on every push are listed under `unstable`. If you are planning to deploy the application, simply choose the latest release. The releases feature a date-based version number, so you can determine the latest version by finding the most recent dated version of release. If you are testing out a pull request that you've submitted to us, match the commit hash of your pull request with the filename of the unstable build to find the war file that matches your build.

If you would like to build your own war file, follow these instructions:

* `grails war` will instruct grails to build the application into a war file.
* the resulting artifact is stored in `build/libs/`.
* We recommend putting at least the date in each war file name to distinguish builds.
* upload and deploy the renamed file as a new application version in beanstalk.

