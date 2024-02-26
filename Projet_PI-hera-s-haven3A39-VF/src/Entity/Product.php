<?php

namespace App\Entity;

use App\Repository\ProductRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Symfony\Component\Validator\Constraints as Assert;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: ProductRepository::class)]
class Product
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'productId')]
    private ?int $productId = null;

    #[ORM\Column(length: 255)]
/**
 * @Assert\NotBlank(message="Please don't leave it blank")
 * @Assert\Length(min=4, 
 * minMessage="Min length is 4 caracters")
 */
private ?string $Product_Name = null;

    #[ORM\Column]
    /**
     * @Assert\NotBlank(message="Please don't leave it blank")
     * @Assert\Positive()
     * @Assert\GreaterThan(value=0, 
     * message="Quantity should be greater than 0.")
     */
    private ?int $Product_Quantity = null;

    #[ORM\Column(length: 255)]
    /**
     * @Assert\NotBlank(message="Please don't leave it blank")
    * @Assert\Regex(pattern="/\d/", 
    * match=false,
    * message="The product label should not contain any digits.")
     */
    private ?string $Product_Label = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    /**
     * @Assert\GreaterThanOrEqual(value="+2 weeks", 
     * message="Expiration date should be at least two weeks from today.")
     * @Assert\NotBlank(message="Please don't leave it blank")
     */
    private ?\DateTimeInterface $Expiration_Date = null;
  

    #[ORM\ManyToOne(inversedBy: 'products')]
    #[ORM\JoinColumn(nullable: false, name: 'Category_Key', referencedColumnName: 'categoryId')]
    private ?Category $Category_Key = null;

    #[ORM\OneToMany(mappedBy: 'Product_Key', targetEntity: DonationProduct::class)]
    private Collection $donationProducts;

    public function __construct()
    {
        $this->donationProducts = new ArrayCollection();
    }

    public function getproductId(): ?int
    {
        return $this->productId;
    }

    public function getProductName(): ?string
    {
        return $this->Product_Name;
    }

    public function setProductName(?string $Product_Name): static
    {
        $this->Product_Name = $Product_Name;

        return $this;
    }

    public function getProductQuantity(): ?int
    {
        return $this->Product_Quantity;
    }

    public function setProductQuantity(?int $Product_Quantity): static
    {
        $this->Product_Quantity = $Product_Quantity;

        return $this;
    }

    public function getProductLabel(): ?string
    {
        return $this->Product_Label;
    }

    public function setProductLabel(?string $Product_Label): static
    {
        $this->Product_Label = $Product_Label;

        return $this;
    }

    public function getExpirationDate(): ?\DateTimeInterface
    {
        return $this->Expiration_Date;
    }

    public function setExpirationDate($Expiration_Date): static
{
    if (is_string($Expiration_Date)) {
        $Expiration_Date = new \DateTime($Expiration_Date);
    }

    $this->Expiration_Date = $Expiration_Date;

    return $this;
}
    public function getCategoryKey(): ?Category
    {
        return $this->Category_Key;
    }

    public function setCategoryKey(?Category $Category_Key): static
    {
        $this->Category_Key = $Category_Key;

        return $this;
    }
    public function __toString()
    {
        return $this->Product_Name;
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
            $donationProduct->setProductKey($this);
        }

        return $this;
    }

    public function removeDonationProduct(DonationProduct $donationProduct): static
    {
        if ($this->donationProducts->removeElement($donationProduct)) {
            // set the owning side to null (unless already changed)
            if ($donationProduct->getProductKey() === $this) {
                $donationProduct->setProductKey(null);
            }
        }

        return $this;
    }    
}
