<dependencies> 
    <dependency> 
        <groupId>org.springframework.boot</groupId> 
        <artifactId>spring-boot-starter-web</artifactId> 
    </dependency> 
    <dependency> 
        <groupId>org.springframework.boot</groupId> 
        <artifactId>spring-boot-starter-data-jpa</artifactId> 
    </dependency> 
    <dependency> 
        <groupId>mysql</groupId> 
        <artifactId>mysql-connector-java</artifactId> 
    </dependency> 
    <dependency> 
        <groupId>org.springframework.boot</groupId> 
        <artifactId>spring-boot-starter-thymeleaf</artifactId> 
    </dependency> 
</dependencies> 
2. Database Entity for Campaign 
java 
 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
 
 
public class Campaign { 
 
     
    GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
    private String name; 
    private String description; 
    private double targetAmount; 
    private double totalDonated; 
 
    // Getters and Setters 
    public Long getId() { 
        return id; 
    } 
 
    public void setId(Long id) { 
        this.id = id; 
    } 
 
    public String getName() { 
        return name; 
    } 
 
    public void setName(String name) { 
        this.name = name; 
    } 
 
    public String getDescription() { 
        return description; 
    } 
 
    public void setDescription(String description) { 
        this.description = description; 
    } 
 
    public double getTargetAmount() { 
        return targetAmount; 
    } 
 
    public void setTargetAmount(double targetAmount) { 
        this.targetAmount = targetAmount; 
    } 
 
    public double getTotalDonated() { 
        return totalDonated; 
    } 
 
    public void setTotalDonated(double totalDonated) { 
        this.totalDonated = totalDonated; 
    } 
} 
3. Database Entity for Donation 
java 
 
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType; 
import javax.persistence.Id; 
 
Entity 
public class Donation { 
 
    Id 
    GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 
    private Long campaignId; 
    private double amount; 
    private String donorName; 
    private String donorEmail; 
 
    // Getters and Setters 
    public Long getId() { 
        return id; 
    } 
 
    public void setId(Long id) { 
        this.id = id; 
    } 
 
    public Long getCampaignId() { 
        return campaignId; 
    } 
 
    public void setCampaignId(Long campaignId) { 
        this.campaignId = campaignId; 
    } 
 
    public double getAmount() { 
        return amount; 
    } 
 
    public void setAmount(double amount) { 
        this.amount = amount; 
    } 
 
    public String getDonorName() { 
        return donorName; 
    } 
 
    public void setDonorName(String donorName) { 
        this.donorName = donorName; 
    } 
 
    public String getDonorEmail() { 
        return donorEmail; 
    } 
 
    public void setDonorEmail(String donorEmail) { 
        this.donorEmail = donorEmail; 
    } 
} 
4. Repository for Campaigns and Donations 
java 
 
import org.springframework.data.jpa.repository.JpaRepository; 
 
public interface CampaignRepository extends JpaRepository<Campaign, Long> { 
} 
 
public interface DonationRepository extends JpaRepository<Donation, Long> { 
} 
5. Donation and Campaign Service 
java 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
 
@Service 
public class CampaignService { 
 
    @Autowired 
    private CampaignRepository campaignRepository; 
 
    public List<Campaign> getAllCampaigns() { 
        return campaignRepository.findAll(); 
    } 
 
    public Campaign getCampaignById(Long id) { 
        return campaignRepository.findById(id).orElseThrow(() -> new 
RuntimeException("Campaign not found")); 
    } 
} 
 
@Service 
public class DonationService { 
 
    @Autowired 
    private DonationRepository donationRepository; 
 
    @Autowired 
    private CampaignRepository campaignRepository; 
 
    public void saveDonation(Donation donation) { 
        Campaign campaign = 
campaignRepository.findById(donation.getCampaignId()).orElseThrow(() -> new 
RuntimeException("Campaign not found")); 
        campaign.setTotalDonated(campaign.getTotalDonated() + donation.getAmount()); 
        campaignRepository.save(campaign); 
        donationRepository.save(donation); 
    } 
} 
6. Controller for Handling Requests 
java 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.*; 
 
import java.util.List; 
 
@RestController 
@RequestMapping("/api") 
public class DonationController { 
 
    @Autowired 
    private CampaignService campaignService; 
 
    @Autowired 
    private DonationService donationService; 
 
    // Get all campaigns 
    @GetMapping("/campaigns") 
    public List<Campaign> getAllCampaigns() { 
        return campaignService.getAllCampaigns(); 
    } 
 
    // Get campaign by ID 
    @GetMapping("/campaign/{id}") 
    public Campaign getCampaignById(@PathVariable Long id) { 
        return campaignService.getCampaignById(id); 
    } 
 
    // Make a donation 
    @PostMapping("/donations") 
    public ResponseEntity<String> makeDonation(@RequestBody Donation donation) { 
donationService.saveDonation(donation); 
return ResponseEntity.ok("Donation successful!"); 
} 
} 
7. Application Entry Point (Main Class) 
java 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication; 
@SpringBootApplication 
public class DonationCampaignApplication { 
public static void main(String[] args) { 
SpringApplication.run(DonationCampaignApplication.class, args); 
} 
} 
1. Database Schema: 