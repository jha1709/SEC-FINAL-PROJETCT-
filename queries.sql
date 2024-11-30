CREATE TABLE campaigns ( 
id BIGINT AUTO_INCREMENT PRIMARY KEY,       
name VARCHAR(255) NOT NULL,                 -- Unique identifier for each campaign -- Name of the campaign 
description TEXT,                           -- Description of the campaign 
target_amount DECIMAL(15, 2) NOT NULL,      -- The target donation amount for the 
campaign 
total_donated DECIMAL(15, 2) DEFAULT 0,     -- The total amount donated so far (starts 
at 0) 
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- The date and time when the 
campaign was created 
); 
Explanation: 
• id: Auto-incrementing primary key to uniquely identify each campaign. 
• name: Name of the campaign (e.g., "Help Children in Need"). 
• description: A brief description of the campaign (e.g., "Help provide education and 
shelter for children"). 
• target_amount: The goal amount for the campaign (e.g., $10,000). 
• total_donated: The total amount donated so far, which will be updated as 
donations come in. 
• created_at: Timestamp to keep track of when the campaign was created. 
2. Creating the donations Table: 
The donations table will store information about individual donations, including the 
donor’s name, email, donation amount, and the campaign they donated to. 
sql 
CREATE TABLE donations ( 
id BIGINT AUTO_INCREMENT PRIMARY KEY,       
campaign_id BIGINT,                         -- Unique identifier for each donation -- Foreign key referencing the campaign that received 
the donation 
amount DECIMAL(15, 2) NOT NULL,             
donor_name VARCHAR(255) NOT NULL,           
donor_email VARCHAR(255) NOT NULL,          -- The donation amount -- Donor's name -- Donor's email address 
donation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date and time of the 
donation 
FOREIGN KEY (campaign_id) REFERENCES campaigns(id) -- Foreign key to link to the 
campaigns table 
); 
Explanation: 
• id: Auto-incrementing primary key to uniquely identify each donation. 
• campaign_id: Foreign key linking to the campaigns table (the campaign to which 
the donation is made). 
• amount: The amount of money donated by the donor. 
• donor_name: Name of the person making the donation. 
• donor_email: Email address of the donor. 
• donation_date: Timestamp of when the donation was made. 
• FOREIGN KEY (campaign_id) REFERENCES campaigns(id): This creates a relationship 
between the donations table and the campaigns table. 
3. Sample Data Insertion: 
Once the tables are created, you can insert some sample data to test the tables: 
Inserting Sample Campaigns: 
sql 
INSERT INTO campaigns (name, description, target_amount) 
VALUES  
('Help Children in Need', 'A campaign to provide education and shelter for children in 
need.', 10000.00), 
('Save the Rainforest', 'A campaign to protect endangered rainforests around the 
world.', 50000.00); 
Inserting Sample Donations: 
sql 
INSERT INTO donations (campaign_id, amount, donor_name, donor_email) 
VALUES 
(1, 200.00, 'John Doe', 'john.doe@example.com'), 
(1, 50.00, 'Jane Smith', 'jane.smith@example.com'), 
(2, 1000.00, 'Alice Brown', 'alice.brown@example.com'); 
4. Query Examples: 
Here are a few SQL queries that you can use to interact with the database: 
a. Query to Get All Campaigns: 
sql 
SELECT * FROM campaigns; 
b. Query to Get a Specific Campaign (by ID): 
sql 
SELECT * FROM campaigns WHERE id = 1; 
c. Query to Get All Donations for a Specific Campaign: 
sql 
SELECT * FROM donations WHERE campaign_id = 1; 
d. Query to Get the Total Donations for a Specific Campaign: 
sql 
SELECT SUM(amount) AS total_donated 
FROM donations 
WHERE campaign_id = 1; 
e. Query to Update the Total Donations of a Campaign: 
This query updates the total donated amount in the campaigns table based on the 
donations received in the donations table. 
sql 
UPDATE campaigns 
SET total_donated = (SELECT SUM(amount) FROM donations WHERE campaign_id = 
campaigns.id) 
WHERE id = 1; 