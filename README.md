# copper Test

Hi, I have tried to create a framework which can upload the json records into system.
There are two feature files in this framework which can allow user to insert the required json
reading from flat files or insert via cucumber scenario data table.

I have used Json serializer / deserializer to parser the json and load/post the payload to - http://localhost:8080/collaterals/.
But,I faced lot of issues in doing the setup of this service, downloaded from (https://github.com/copperexchange/sdet-test-task),
However, couldn’t explore much with service, as I was getting the below errors 
********************************************************************************************************************
**"Whitelabel Error Page

This application has no explicit mapping for /error, so you are seeing this as a fallback.

Sun May 15 16:14:23 BST 2022
There was an unexpected error (type=Method Not Allowed, status=405).**
********************************************************************************************************************
I tried inserting some records using postman (also) to this service but still gets the below error

**Please note**- All the post request were failing with below error "status": 400, "error": "Bad Request",
                 

**Note- I assume that it's a standalone service should take request(get/post), without any changes?
      if not, I am afraid, I don't have much knowledge on java swings to change/alter/edit it.
      Also, no directions were provided in read me file, except 2 downloads.
**

It took most of my Sunday afternoon, which was more than specified time by HR. So couldn’t continue further to explore.
However, this exercise has given me hint about the project, it seems/sounds quite challenging and exiting.
I would love to prepare / explore and learn more on this journey.

Please share the feedback. 

Thanks
Amit
