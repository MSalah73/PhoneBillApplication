package edu.pdx.cs410J.ms24;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.HashSet;
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
   * This <code>String</code> is a representation of the customer's name.
   */
  private String customer;
  /**
   * This <code>HasSet</code> of type <code>PhoneCall</code> is container
   * the hold all the customer's <code>PhoneCall</code>s.
   */
  private HashSet<PhoneCall> phoneCalls = new HashSet<>();
}
