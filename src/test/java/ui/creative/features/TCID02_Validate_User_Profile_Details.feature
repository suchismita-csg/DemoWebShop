##################################################################################################
# Author : Surya K
# Test Case ID: TCID02
##################################################################################################

Feature: Demo Webshop application

  @Profile @TCID02 @Reg
  Scenario Outline: TCID02 Validate User Profile
    Given I sign in to the Demo Web Shop with valid credentials
    And I close the browser window


  Examples: 
    | TCID   |
    | TCID03 |
