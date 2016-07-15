Feature: DataPool

  Scenario: Select test data by criteria
    Given there is a "Address" which has
    | City | is | Zadonsk |
    When I request address details
    Then all "Address" details are the same as expected
    And Address has street "per. Vesennij"


  Scenario: Select test data by criteria 2
    Given there is a "User" which has
      | Age | is_greater | 20 |
      | FirstUserName | is | Brazina |
    When I request user details
    Then User has last name "Litsev"
    And all "User" details are the same as expected
