##################################################################################################
# Author : Suchismita
# Test Case ID: TCID01
##################################################################################################

Feature: Demo Webshop application

  @Purchase @TCID01 @Reg
  Scenario Outline: TCID01 Purchase Book
    Given I sign in to the Demo Web Shop with valid credentials
    Then I navigate to Books page
    And I select book and add to cart
    Then I order the book 
    Then I validate book purchased successfully
    And I close the browser window

  Examples: 
    | TCID   |
    | TCID01 |
