<?php

namespace App\Form;

use App\Entity\DonationP;
use App\Entity\Account;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\Extension\Core\Type\DateType;
use Symfony\Component\Form\Extension\Core\Type\IntegerType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;


use Symfony\Component\OptionsResolver\OptionsResolver;
class DonationPType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('donation_product_name', TextType::class, [
                'label' => 'Product Name',
            ])
            ->add('donation_product_quantity', IntegerType::class, [
                'label' => 'Product Quantity',
                'required' => false,
            ])
            ->add('donation_product_label', TextType::class, [
                'label' => 'Product Label',
            ])
            ->add('donation_product_expiration_date', DateType::class, [
                'label' => 'Product Expiration Date',
                'widget' => 'single_text',
                'required' => false,
            ])            
            ->add('donationP_date', DateType::class, [
                'label' => 'Donation Date',
                'widget' => 'single_text',
                'required' => false,
            ])    
            ->add('donationP_type', ChoiceType::class, [
                'label' => 'Product Type',
                'choices' => [
                    'Medical' => 'medical',
                    'Hygienic' => 'hygienic',
                    'Food Supplies' => 'food_supplies',
                ],
                'required' => false,
                'placeholder' => 'Select product type', // optionnel, ajoutez ceci si vous voulez une option par défaut
            ])
            ->add('Account_Key',EntityType::class, [
                'class' => Account::class,
                'label' => 'Account',
                'required' => false,    
                'placeholder' => '',
            ])
        ;
        
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => DonationP::class,
        ]);
    }
}