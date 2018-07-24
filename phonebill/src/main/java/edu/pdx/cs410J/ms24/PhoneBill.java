package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;

/**
 * This Class is a representation of a <code>PhoneBill</code>.
 *
 * @author Zack Salah
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
  /**
   * Create a empty <code>PhoneBill</code>.
   */
  PhoneBill() {
    super();
  }

  /**
   * Creates a new <code>PhoneBill</code>.
   *
   * @param name
   *        The customer's Name
   */
  PhoneBill(final String name) {
    super();
    customer = name;
  }

  /**
   * Creates a new <code>PhoenBill</code>.
   *
   * @param name
   *        The customer's name
   * @param toAdd
   *        Customer's first <code>PhoneCall</code> information
   */
  PhoneBill(final String name, final PhoneCall toAdd) {
    super();
    customer = name;
    addPhoneCall(toAdd);
  }

  /**
   * Creates a new <code>PhoneBill</code> from array of <code>PhoneBill</code>s.
   * @param name
   * The name of customer
   * @param bills
   * array of <code>PhoneBill</code> objects.
   */
  PhoneBill(final String name, PhoneBill... bills){
    super();
    customer = name;
    copyPhoneBills(bills);
  }

  /**
   * Gets the customer's name by returning stored name.
   * @return customer's name as <code>String</code>
   */
  @Override
  public final String getCustomer() {
    return customer;
  }

  /**
   * Adding a <code>Phonecall</code> information to customer's
   * <code>PhoneBill</code>.
   * @param phoneCall
   *        The customer's new <code>PhoneCall</code> information.
   */
  @Override
  public final void addPhoneCall(final PhoneCall phoneCall) {
    phoneCalls.add(phoneCall);
    Collections.sort(phoneCalls);
  }

  /**
   * Returns a <code>Collation</code>s Of the customer's
   * <code>PhoneCall</code>s
   * @return <code>Collection</code> of type <code>PhoneCall</code>
   */
  @Override
  public final Collection<PhoneCall> getPhoneCalls() {
    return phoneCalls;
  }
  /**
   * Notifies the caller that the PhoneBill object is empty.
   * @return true on empty PhoneBill, otherwise false.
   */
  public boolean isEmpty(){
    return (customer == null || customer.isEmpty()) && phoneCalls.isEmpty();
  }

  /**
   * This function copy the <code>PhoneCall</code>s in each <code>PhoneBill</code>s
   * into one <code>PhoneBill</code> object.
   * All the bill must have the same's customer's name.
   * @param bills
   * an array of <code>PhoneBill</code>s
   */
  private void copyPhoneBills(PhoneBill... bills){
    for(var bill : bills){
      if(bill != null && customer.equals(bill.customer)) {
        phoneCalls.addAll(bill.getPhoneCalls());
        Collections.sort(phoneCalls);
      } else{
        throw new InvalidParameterException("PhoneBills does not contain the same customer");
      }
    }
  }
  /**
   * This <code>String</code> is a representation of the customer's name.
   */
  private String customer;
  /**
   * This <code>ArrayList</code> of type <code>PhoneCall</code> is container
   * the hold all the customer's <code>PhoneCall</code>s.
   */
  private ArrayList<PhoneCall> phoneCalls = new ArrayList<>();
}
