package org.apache.commons.lang;

/**
 * Minimal stand-in for commons-lang 2.6's {@code StringUtils}, backed by commons-lang3.
 * <p>
 * commons-lang 2.6 carries CVE-2025-48924 and there is no fix on the 2.x line -- the
 * advisory's remedy is commons-lang3 3.18+, which is a different artifact in a different
 * package. The only thing left in the WAR referencing the 2.x package was the prebuilt
 * {@code org.hl7.security.ds4p.contentprofile} jar, whose
 * {@code SecurityXSIProvider$TemplateComparator} calls {@code StringUtils.isEmpty(String)}.
 * That jar sits on a live path -- {@code DS4PUtil.validateAsDS4P} registers
 * {@code SecurityXSIProvider} in MDHT's {@code XSITypeProviderRegistry} -- so the
 * dependency could not simply be dropped without risking {@code NoClassDefFoundError} on
 * DS4P validations.
 * <p>
 * Renaming the package inside the prebuilt jar would not work either: lang3's signature is
 * {@code isEmpty(CharSequence)}, so the existing {@code isEmpty(String)} callsite would
 * fail with {@code NoSuchMethodError}.
 * <p>
 * So this class supplies that one method and delegates to lang3.
 * {@code WEB-INF/classes} precedes {@code WEB-INF/lib} on the webapp classpath, so it
 * satisfies the reference with commons-lang 2.6 absent from the build.
 * <p>
 * Add methods here only if a new commons-lang 2.x reference appears. To check, scan the
 * built WAR's jars for the {@code org/apache/commons/lang/} package (excluding
 * {@code lang3}).
 */
public class StringUtils {

    /**
     * Public to match commons-lang 2.6, which left {@code StringUtils} instantiable on
     * purpose so it could be used from tools needing a JavaBean instance.
     */
    public StringUtils() {
        super();
    }

    /**
     * Checks if a String is empty ("") or null. Identical in behaviour to
     * commons-lang 2.6's {@code isEmpty} for {@code String} input.
     */
    public static boolean isEmpty(String str) {
        return org.apache.commons.lang3.StringUtils.isEmpty(str);
    }
}
