package socialmediawrapper;

import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import model.ModalFbUserProfile;


public interface SocialMediaInterface
{
   public void login(ModalFbUserProfile modelfbuser);
   public void getfriend_list(JSONObject jsonobject);
   public void getProfileInformation_of_google_plus(Person person,String email);
   public void TwitterInfo(String modelfbuser);

}
