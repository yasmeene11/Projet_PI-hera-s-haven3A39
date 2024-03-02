<?php

namespace App\Entity;

use App\Repository\CategoryRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass: CategoryRepository::class)]
class Category
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'categoryId')]
    private ?int $categoryId = null;

    #[ORM\Column(length: 255)]
    /**
 * @Assert\NotBlank(message="Don't leave it blank")
 */
    private ?string $Product_Type = null;

    #[ORM\Column(length: 255)]
    /**
 * @Assert\NotBlank(message="Don't leave it blank")
 */
    private ?string $Product_Source = null;

    #[ORM\OneToMany(mappedBy: 'Category_Key', targetEntity: Product::class)]
    private Collection $products;

    public function __construct()
    {
        $this->products = new ArrayCollection();
    }

    public function getcategoryId(): ?int
    {
        return $this->categoryId;
    }

    public function getProductType(): ?string
    {
        return $this->Product_Type;
    }

    public function setProductType(string $Product_Type): static
    {
        $this->Product_Type = $Product_Type;

        return $this;
    }
    public function __toString()
    {
        return $this->Product_Type;
    }
    public function getProductSource(): ?string
    {
        return $this->Product_Source;
    }

    public function setProductSource(string $Product_Source): static
    {
        $this->Product_Source = $Product_Source;

        return $this;
    }

    /**
     * @return Collection<int, Product>
     */
    public function getProducts(): Collection
    {
        return $this->products;
    }

    public function addProduct(Product $product): static
    {
        if (!$this->products->contains($product)) {
            $this->products->add($product);
            $product->setCategoryKey($this);
        }

        return $this;
    }

    public function removeProduct(Product $product): static
    {
        if ($this->products->removeElement($product)) {
            // set the owning side to null (unless already changed)
            if ($product->getCategoryKey() === $this) {
                $product->setCategoryKey(null);
            }
        }

        return $this;
    }
}