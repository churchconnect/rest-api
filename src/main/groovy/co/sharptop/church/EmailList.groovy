package co.sharptop.church

class EmailList extends Entry {

    String title
    List<ContentfulPerson> recipients

    static String contentfulContentType = "emailList"

}
