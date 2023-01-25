import com.example.random.application.RandomNumberGenerator;
import com.example.random.application.business.FastRandomNumberGenerator;
import com.example.random.application.business.SecureRandomNumberGenerator;

module com.example.random {
	exports com.example.random.application;
	provides RandomNumberGenerator
	with FastRandomNumberGenerator,SecureRandomNumberGenerator;
}