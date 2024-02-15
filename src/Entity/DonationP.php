<?php

namespace App\Entity;

use App\Repository\DonationPRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\DBAL\Types\Types;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DonationPRepository::class)]
class DonationP
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'donationPId')]
    private ?int $donationPId = null;

    #[ORM\Column(length: 255)]
    private ?string $donation_product_name = null;

    #[ORM\Column]
    #[Assert\GreaterThan(value: 0, message: "Quantity should be greater than 0.")]
    private ?int $donation_product_quantity = null;

    #[ORM\Column(length: 255)]
    private ?string $donation_product_label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\GreaterThanOrEqual(value: "+2 weeks", message: "Expiration date should be at least two weeks from today.")]
    private ?\DateTimeInterface $donation_product_expiration_date = null;

    

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\EqualTo("today", message: "The donation date should be today")]
    private ?\DateTimeInterface $donationP_date = null;

    #[ORM\ManyToOne(inversedBy: 'donationPs')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    

    #[ORM\Column(length: 255)]
    private ?string $donationP_type = null;

    #[ORM\OneToMany(mappedBy: 'Donation_Key', targetEntity: DonationProduct::class)]
    private Collection $donationProducts;

    public function __construct()
    {
        $this->donationProducts = new ArrayCollection();
        $this->donationP_Date = new \DateTime();
    }

   

    

   

   
    public function getDonationPId(): ?int
    {
        return $this->donationPId;
    }

    public function getDonationProductName(): ?string
    {
        return $this->donation_product_name;
    }

    public function setDonationProductName(string $donation_product_name): static
    {
        $this->donation_product_name = $donation_product_name;

        return $this;
    }

    public function getDonationProductQuantity(): ?string
    {
        return $this->donation_product_quantity;
    }

    public function setDonationProductQuantity(string $donation_product_quantity): static
    {
        $this->donation_product_quantity = $donation_product_quantity;

        return $this;
    }

    public function getDonationProductLabel(): ?string
    {
        return $this->donation_product_label;
    }

    public function setDonationProductLabel(string $donation_product_label): static
    {
        $this->donation_product_label = $donation_product_label;

        return $this;
    }

    public function getDonationProductExpirationDate(): ?\DateTimeInterface
    {
        return $this->donation_product_expiration_date;
    }

    public function setDonationProductExpirationDate(\DateTimeInterface $donation_product_expiration_date): static
    {
        $this->donation_product_expiration_date = $donation_product_expiration_date;

        return $this;
    }

  

    public function getDonationPDate(): ?\DateTimeInterface
    {
        return $this->donationP_date;
    }

    public function setDonationPDate(\DateTimeInterface $donationP_date): static
    {
        $this->donationP_date = $donationP_date;

        return $this;
    }

    public function getAccountKey(): ?Account
    {
        return $this->Account_Key;
    }

    public function setAccountKey(?Account $Account_Key): static
    {
        $this->Account_Key = $Account_Key;

        return $this;
    }

   
    public function getDonationPType(): ?string
    {
        return $this->donationP_type;
    }

    public function setDonationPType(string $donationP_type): static
    {
        $this->donationP_type = $donationP_type;

        return $this;
    }

    /**
     * @return Collection<int, DonationProduct>
     */
    public function getDonationProducts(): Collection
    {
        return $this->donationProducts;
    }

    public function addDonationProduct(DonationProduct $donationProduct): static
    {
        if (!$this->donationProducts->contains($donationProduct)) {
            $this->donationProducts->add($donationProduct);
            $donationProduct->setDonationKey($this);
        }

        return $this;
    }

    public function removeDonationProduct(DonationProduct $donationProduct): static
    {
        if ($this->donationProducts->removeElement($donationProduct)) {
            // set the owning side to null (unless already changed)
            if ($donationProduct->getDonationKey() === $this) {
                $donationProduct->setDonationKey(null);
            }
        }

        return $this;
    }

   

   

  
}
