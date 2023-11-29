package team.jit.wojciechzieba.secretsantaundertests.architecture;

import java.util.List;
import java.util.stream.StreamSupport;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static java.util.Collections.emptyList;

@AnalyzeClasses(
        packages = ArchitectureTest.BASE_PACKAGE
)
class ArchitectureTest {


    static final String BASE_PACKAGE = "team.jit.wojciechzieba.secretsantaundertests";

    private String DOMAIN = "$BASE_PACKAGE.domain..";
    private String ADAPTER = "$BASE_PACKAGE.adapter..";
    private String CONFIG = "$BASE_PACKAGE.config..";
    private String INFRASTRUCTURE = "$BASE_PACKAGE.infrastructure..";
    private String SHAREDKERNEL = "$BASE_PACKAGE.sharedkernel..";

    private List<String> ALL_PACKAGES = List.of(DOMAIN, ADAPTER, CONFIG, SHAREDKERNEL, INFRASTRUCTURE);


    @ArchTest
    ArchRule REST_ENDPOINTS_SHOULD_HAVE_PROPER_SUFFIX =
            classes().
                    that()
                    .areAnnotatedWith(RestController.class)
                    .and()
                    .resideInAPackage("..adapter.api..")
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    ArchRule DOMAIN_SHOULD_NOT_DEPEND_ON_ADAPTER =
            noClasses()
                    .that()
                    .resideInAPackage("..domain..")
                    .should()
                    .dependOnClassesThat().resideInAnyPackage(ADAPTER);


    @ArchTest
    ArchRule SERVICE_CLASS_SHOULD_BE_TESTED =
            classes().that().haveSimpleNameContaining("Service")
                    .should(haveACorrespondingClassEndingWith("SequenceSpec"));


    private ArchCondition<JavaClass> haveACorrespondingClassEndingWith(String testClassSuffix) {

        return new ArchCondition<JavaClass>("Has test") {
            private List<String> alreadyTestedClasses = emptyList();

            @Override
            public void init(Iterable<JavaClass> allObjectsToTest) {
                alreadyTestedClasses = StreamSupport.stream(allObjectsToTest.spliterator(), false)
                        .map(JavaClass::getSimpleName)
                        .filter(it -> it.endsWith(testClassSuffix))
                        .map(it -> it.substring(0, it.lastIndexOf(-testClassSuffix.length())))
                        .toList();

            }

            @Override
            public void check(final JavaClass javaClass, final ConditionEvents conditionEvents) {
                if (javaClass.getSimpleName().endsWith(testClassSuffix)) {
                    var satisfied = alreadyTestedClasses.contains(javaClass.getSimpleName());
                    conditionEvents.add(new SimpleConditionEvent(javaClass, satisfied, STR. "Class \{ javaClass } has corresponding test class" ));
                }
            }
        };
    }
}