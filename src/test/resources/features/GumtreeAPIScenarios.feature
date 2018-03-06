Feature: Verify Gumtree API
As a consumer of gumtree API
I should be able to interact with the end points
And validate results


Scenario Outline: Validate Gumtree API Interactions
Given I have navigated to users page
When I extract the response
Then I extract a random userId and print its email to the console
Then I make a get call for all the posts for the userId from above
And I validate the post Ids for each post
Then I make a post based on the userId from above with valid "<title>" and "<body>"
And I validate the response status for the post request

  Examples:
    |title          | body                          |
    | gumtree title | gumtree body                  |

