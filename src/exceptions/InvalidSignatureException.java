package exceptions;

public class InvalidSignatureException extends Exception {
    public InvalidSignatureException() {
        super("The signature is invalid. It should be in the form of strings without spaces concatenated with greater than signs.\nExample: compilation_uni > unit_declaration_q > package_declaration > visible_part_declarative_items_ql > ordinary_type_declaration");
    }
}
