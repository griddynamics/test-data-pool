package com.griddynamics.qa.datapool.examples.cuke;

import com.griddynamics.qa.datapool.DataPool;
import com.griddynamics.qa.datapool.FilterCriterion;
import com.griddynamics.qa.datapool.datatype.IDataType;
import com.griddynamics.qa.datapool.examples.aux.Address;
import com.griddynamics.qa.datapool.examples.aux.User;
import com.griddynamics.qa.datapool.examples.dbfetcher.DbFetcherExample;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Alexey Lyanguzov.
 */
public class DataPoolSteps {
    private IDataType data;
    private IDataType returnedData;

    static {
        //Emulates DataPool fetching
        try {
            DbFetcherExample.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Given("^there is a \"([^\"]*)\" which has$")
    public void thereIsAWhoHas(String dataTypeName, DataTable dataTable) throws Throwable {
        data = getDataFromDataPool(dataTypeName, dataTable);
    }

    @Then("^Address has street \"([^\"]*)\"$")
    public void addressHasStreet(String street) throws Throwable {
        Address address = (Address)data;
        assertEquals(street, address.getStreet1());
    }

    @Then("^User has last name \"([^\"]*)\"$")
    public void userHasLastName(String lastName) throws Throwable {
        User user = (User)data;
        assertEquals(lastName, user.getLastUserName());
    }

    @When("^I request user details$")
    public void iRequestUserDetails() throws Throwable {
        getUserDetails();
    }

    @When("^I request address details$")
    public void iRequestAddressDetails() throws Throwable {
        getAddressDetails();
    }

    private <T extends IDataType> T getDataFromDataPool(String dataTypeName, DataTable dataTable){
        List<FilterCriterion> criterions = new ArrayList<>();
        for(List<String> criteriaRow : dataTable.raw()){
            criterions.add(new FilterCriterion(criteriaRow.get(0), criteriaRow.get(1), criteriaRow.get(2)));
        }
        return (T)DataPool.find(dataTypeName, criterions.toArray(new FilterCriterion[0])).get(0);
    }

    @And("^all \"([^\"]*)\" details are the same as expected$")
    public void allDetailsAreTheSameAsExpected(String typeName) throws Throwable {
        assertEquals(data, returnedData);
    }

    // Emulates interaction with SUT
    private void getUserDetails(){
        if(data != null){
            User user = (User)data;
            returnedData = User.newUser();
            user.listAllProperties().forEach(p -> returnedData.set(p, user.get(p, Object.class)));
        }
    }

    // Emulates interaction with SUT
    private void getAddressDetails(){
        returnedData = DataPool.find(Address.class).get(2);
    }
}
